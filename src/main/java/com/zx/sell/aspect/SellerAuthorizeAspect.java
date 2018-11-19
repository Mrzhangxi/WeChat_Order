package com.zx.sell.aspect;

import com.zx.sell.constant.CookieConstant;
import com.zx.sell.constant.RedisConstant;
import com.zx.sell.exception.SellException;
import com.zx.sell.exception.SellerAuthorizeException;
import com.zx.sell.utils.CookieUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("execution(public * com.zx.sell.controller.Seller*.*(..))" +
    "&& !execution(public * com.zx.sell.controller.SellerUserController.*(..))")
    public void verify(){}

    @Before("verify()")
    public void doVerify() {
        //获取HttpRequest
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //查询cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie == null) {
//            log.warn("Cookie中查不到token");
            throw new SellerAuthorizeException();
        }
        //去Redis里查询
        String tokenValue = redisTemplate.opsForValue().get(String.format(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue())));
        if (tokenValue == null || StringUtils.isEmpty(tokenValue)) {
//            log.warn("Redis中查不到记录");
            throw new SellerAuthorizeException();
        }
    }
}
