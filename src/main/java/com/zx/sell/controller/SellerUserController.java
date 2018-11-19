package com.zx.sell.controller;

import com.zx.sell.Service.SellerService;
import com.zx.sell.constant.CookieConstant;
import com.zx.sell.constant.RedisConstant;
import com.zx.sell.dataobject.SellerInfo;
import com.zx.sell.enums.ResultEnum;
import com.zx.sell.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerService sellerService;
    @Autowired
    private StringRedisTemplate redisTemplate;


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam("openid") String openid,
                              HttpServletResponse response,
                              Map<String, Object> map) {

        //1.openId和数据库进行匹配
        SellerInfo sellerInfo = sellerService.getSellerInfoByOpenId(openid);
        if (sellerInfo == null ) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error");
        }
        //2.设置tokenzhi至Redis
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;//过期时间
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), openid, expire, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set("abc", "zhangxi");

        //3.设置token至cookie
        CookieUtil.set(response, CookieConstant.TOKEN, token, expire);
        //跳转
        return new ModelAndView("redirect:/seller/order/list");
    }


    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Map<String, Object> map){
        //从Cookie里查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        System.out.println(cookie.getValue());
        if (cookie != null) {
            //清除Redis
            System.out.println("清除Redis");
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
            redisTemplate.opsForValue().getOperations().delete("abc");
            //清除cookie
            CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
        }
        map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success", map);
    }

}
