package com.zx.sell.controller;

import com.zx.sell.Service.BuyerService;
import com.zx.sell.Service.OrderService;
import com.zx.sell.ViewObject.ResultVO;
import com.zx.sell.converter.OrderForm2OrderDTOConverter;
import com.zx.sell.dto.OrderDTO;
import com.zx.sell.enums.ResultEnum;
import com.zx.sell.exception.SellException;
import com.zx.sell.form.OrderForm;
import com.zx.sell.utils.ResultVOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private BuyerService buyerService;

    //创建订单
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        System.out.println("create方法被正确执行");
        if (bindingResult.hasErrors()) {
            System.out.println("创建订单参数不正确");
            throw new SellException(ResultEnum.PARAM_ERROR,
                    bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (orderDTO == null || CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
//            log.error("购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO createResult = orderService.create(orderDTO);

        Map<String, String> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());

        return ResultVOUtils.success(map);

    }
    //订单列表
    @RequestMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size){
        if (StringUtils.isEmpty(openid)){
//            log.error("查询订单列表：openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        List<OrderDTO> list = orderService.getOrderDTOsByBuyer(openid, size, page);
//        list.get(0).setOrderDetailList(null);
        return ResultVOUtils.success(list);
    }

    //订单详情
    @RequestMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId) {
//        TODO 此处存在安全性问题，任何人都可以通过orderID访问订单，有越权问题
//        OrderDTO orderDTO = orderService.getOrderDTOByID(orderId);
        OrderDTO orderDTO = buyerService.getBuyerOrderByOrderID(openid, orderId);
        return ResultVOUtils.success(orderDTO);
    }

    //取消订单
    @RequestMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId) {
        //TODO 同样不安全
//        OrderDTO orderDTO = orderService.getOrderDTOByID(orderId);
        OrderDTO orderDTO = buyerService.cancelOrder(openid, orderId);
//        orderService.cancel(orderDTO);
        return ResultVOUtils.success();
    }
}
