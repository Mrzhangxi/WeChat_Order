package com.zx.sell.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/ceshi")
public class ceshiController {

    @RequestMapping("/orderdto")
    public ModelAndView ceshiOrderDTO(){
        System.out.println("orderdto被访问");
        return new ModelAndView("ceshi");
    }
}
