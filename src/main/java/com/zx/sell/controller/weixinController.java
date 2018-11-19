package com.zx.sell.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/weixin")
public class weixinController {

//    微信服务号信息
    private final String APPID = "wx984319a4e904ac05";
    private final String APPSECRET = "cccff41427d9c03787905d5cdc5b2124";

//    动态获取的微信信息
    private String code = "";
    private String access_token = "";
    private String openid = "";
//    private String access_token = "";

    @RequestMapping("/auth")
    public void auth(@Param("code") String code, @Param("state") String state){
        System.out.println("进入auth方法, code是：" + code + ",   state是：" + state);
        this.code = code;
        String access_token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APPID + "&secret=" + APPSECRET + "&code=" + code + "&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String resopen = restTemplate.getForObject(access_token_url, String.class);
        System.out.println(resopen);
//        openid = resopen.getopenid
//        access_token = resopen.getaccess_token;
        String user_info_url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + "15_TbZMp6SDin0Wl4enRBGC9Vonm1ybUE8jUh81k4fJ1NsTN3mgFyaviKn1P8F1A4iifyCNrKN_0fQddMXOBFSKcLPqFI2ZkU740S3XIw0Y84Y" + "&openid=" + "oz8Wn1JM7SGfp4zf9Pqo1sxyJ2Hw" + "&lang=zh_CN";
    }

}
