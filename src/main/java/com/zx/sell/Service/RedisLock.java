package com.zx.sell.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 加锁
     * @param key
     * @param value 当前时间+超时时间
     * @return
     */
    public boolean lock(String key, String value) {
        //向Reids写入flag，如果flag已经存在，说明已经上锁
        if(redisTemplate.opsForValue().setIfAbsent(key, value)){//此处setIfAbsent方法是Redis原生的Setnx的java实现
            return true;
        }

        //判断是否过期，取出被锁上的那次操作存入的flag值，flag值为存入时间+超时时间
        String currentValue = redisTemplate.opsForValue().get(key);
        //如果锁过期
        //判断，上次存入的flag值小于现在的时间，说明已经超过了超时时间，该锁已经过期
        if(!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()){
            //获取上一个锁的时间
            //锁过期后，用当前操作线程的锁值替换上次锁的值，因为Redis的单线程特性，可能会有多条线程同时进入到这里，但是下边的代码同时只会被一条执行
            //用新值替换旧值，并取出旧值
            String oldValue = redisTemplate.opsForValue().getAndSet(key, value);
            //判断取出的旧值是否相等，等于说明正常，解锁，不等于说明在旧锁解锁之后，已经被其他新线程抢占到了资源
            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 解锁
     * @param key
     * @param value
     */
    public void unlock(String key, String value){
        try {
            String currentValue = redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (Exception e) {
            System.out.println("Redis分布式锁报错：" + e.getMessage());
        }
    }
}
