package com.zx.sell.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @RequestMapping("/tologin")
    public ModelAndView toLogin(){
        return new ModelAndView("login/tologin");
    }

}
