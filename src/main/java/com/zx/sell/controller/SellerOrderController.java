package com.zx.sell.controller;

import com.zx.sell.Service.OrderService;
import com.zx.sell.dto.OrderDTO;
import com.zx.sell.enums.ResultEnum;
import com.zx.sell.exception.SellException;
import freemarker.template.SimpleDate;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//卖家端订单
@Controller
@RequestMapping("/seller/order")
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 订单列表
     * @param page 页码
     * @param size 页面大小
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "5") Integer size,
                             Map<String, Object> map) {
        Integer sumPage = 0;
        List<OrderDTO> orderDTOList = orderService.getOrderDTOs(size, page);
        List<OrderDTO> sumorderDTOList = orderService.getOrderDTOs(999, 1);
        if (sumorderDTOList.size() % size == 0){
            sumPage = sumorderDTOList.size() / size;
        } else {
            sumPage = (sumorderDTOList.size() / size) + 1;
        }
        for (OrderDTO orderDTO : orderDTOList){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            orderDTO.setViewCreateTime(simpleDateFormat.format(orderDTO.getCreateTime()));
        }
        map.put("orderDTOList", orderDTOList);
        map.put("sumPage", sumPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("order/list", map);
    }

    /**
     * 取消订单
     * @param orderId
     * @param map
     * @return
     */
    @RequestMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId,
                               Map<String, Object> map){
        try {
            OrderDTO orderDTO = orderService.getOrderDTOByID(orderId);
            orderService.cancel(orderDTO);
        } catch (SellException e) {
            System.out.println("【卖家端取消订单】发生异常{}");
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success");
    }

    /**
     * 订单详情
     * @param orderId
     * @param map
     * @return
     */
    @RequestMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
        OrderDTO orderDTO = new OrderDTO();
        try {
            orderDTO = orderService.getOrderDTOByID(orderId);
        }catch (SellException e) {
//            log.error("【卖家端查询订单详情】发生异常{}", e);
            System.out.println("卖家端查询订单详情】发生异常");
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }

        map.put("orderDTO", orderDTO);
        return new ModelAndView("order/detail", map);
    }

    /**
     * 完结订单
     * @param orderId
     * @param map
     * @return
     */
    @RequestMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
        OrderDTO orderDTO = new OrderDTO();
        try {
            orderDTO = orderService.getOrderDTOByID(orderId);
            orderService.finish(orderDTO);
        }catch (SellException e) {
//            log.error("【卖家端查询订单详情】发生异常{}", e);
            System.out.println("卖家端完结订单发生异常");
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success", map);
    }

    @RequestMapping("/ceshi")
    public ModelAndView ceshi(){
        System.out.println("测试方法");
        Map map = new HashMap();
        return new ModelAndView("fceshi", map);
    }
}
