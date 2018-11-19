package com.zx.sell.controller;

import com.zx.sell.config.WechatAccountConfig;
import com.zx.sell.enums.ResultEnum;
import com.zx.sell.exception.SellException;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;

@Controller()
@RequestMapping("/wechat")
public class WechatController {

    @Autowired
    private WxMpService wxMpService;

    @RequestMapping(value = "/authorize",method = RequestMethod.GET)
    public String authorize(@Param("returnUrl") String returnUrl){
        //配置
        //调用方法
        String url = "http://www.bnkeji.top/sell/wechat/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl));

        System.out.println("更新网页授权，code是：" + redirectUrl);
        return "redirect:" + redirectUrl;
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl) {
        System.out.println("userInfo执行");
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = null;
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
//            log.error("【微信网页授权】{}", e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR, e.getError().getErrorMsg());
        }

        String openId = wxMpOAuth2AccessToken.getOpenId();
        System.out.println(returnUrl + "?openid=" + openId);
        return "redirect:" +  "http://" + returnUrl + "?openid=" + openId;
    }
}
