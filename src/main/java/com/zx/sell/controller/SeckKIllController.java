package com.zx.sell.controller;

import com.zx.sell.Service.SecKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SeckKIllController {

    @Autowired
    private SecKillService secKillService;

    /**
     * 查询秒杀活动特价商品的信息
     * @param productId
     * @return
     * @throws Exception
     */
    @RequestMapping("/query/{productId}")
    @ResponseBody
    public String query(@PathVariable String productId) throws Exception{
        //查库存
        System.out.println(productId);
        String result = secKillService.querySecKillProductInfo(productId);
        return result;
    }

    /**
     * 秒杀，没有抢到返回提示“哎哟喂，没抢到”,抢到了会返回剩余库存
     * @param productId
     * @return
     * @throws Exception
     */
    @RequestMapping("/order/{productId}")
    @ResponseBody
    public String skill(@PathVariable String productId) throws Exception{
        System.out.println("@Skill request, productId: " + productId);
        //秒杀
        secKillService.orderProductMockDiffUser(productId);
        //查库存
        return secKillService.querySecKillProductInfo(productId);
    }
}
