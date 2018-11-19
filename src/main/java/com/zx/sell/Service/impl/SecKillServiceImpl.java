package com.zx.sell.Service.impl;

import com.zx.sell.Service.RedisLock;
import com.zx.sell.Service.SecKillService;
import com.zx.sell.enums.ResultEnum;
import com.zx.sell.exception.SellException;
import com.zx.sell.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SecKillServiceImpl implements SecKillService {

    private static final int TIMEOUT = 10*1000;//超时时间

    @Autowired
    private RedisLock redisLock;

    static Map<String, Integer> products;
    static Map<String, Integer> stock;
    static Map<String, String> orders;
    {
//        模拟多个表，商品信息表、库存表、秒杀成功订单表
        products = new HashMap<>();
        stock = new HashMap<>();
        orders = new HashMap<>();
        products.put("123456", 100000);
        stock.put("123456", 100000);
    }

    private String queryMap(String productId){
        return "国庆活动，商品限量：" + products.get(productId) +
                "还剩：" + stock.get(productId)+ "份， " +
                "该商品成功下单用户为：" + orders.size() + "人";
    }

    public String querySecKillProductInfo(String productId) {
        System.out.println("queryKill方法");
        return this.queryMap(productId);
    }

    public void orderProductMockDiffUser(String productId){

        //加锁
        long time = System.currentTimeMillis() + TIMEOUT;
        if (!redisLock.lock(productId, String.valueOf(time))){
            throw new SellException(ResultEnum.LOCK);
        }
        //查询库存，如果为0则活动结束
        int stockNum = stock.get(productId);
        if (stockNum == 0) {
            throw new SellException(ResultEnum.PREFERENTIALEND);
        } else {
            //下单
            orders.put(KeyUtil.genUniqueKey(), productId);
            //减库存
            stockNum = stockNum - 1;
            try{
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            stock.put(productId, stockNum);
        }

//        解锁
        redisLock.unlock(productId, String.valueOf(time));
    }
}
