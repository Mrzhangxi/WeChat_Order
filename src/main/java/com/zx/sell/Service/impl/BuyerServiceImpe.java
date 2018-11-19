package com.zx.sell.Service.impl;

import com.zx.sell.Service.BuyerService;
import com.zx.sell.Service.OrderService;
import com.zx.sell.dto.OrderDTO;
import com.zx.sell.enums.OrderStatusEnum;
import com.zx.sell.enums.ResultEnum;
import com.zx.sell.exception.SellException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuyerServiceImpe implements BuyerService {

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO getBuyerOrderByOrderID(String openid, String orderid) {
        return checkOrderOwner(openid, orderid);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid, orderId);
        if (orderDTO == null){
//            log.error("取消订单错误，查不到订单");
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDTO);
    }

    private OrderDTO checkOrderOwner(String openid, String orderid){
        OrderDTO orderDTO = orderService.getOrderDTOByID(orderid);
        if(orderDTO == null){
            return null;
        }
        if(!orderDTO.getBuyerOpenid().equals(openid)){
//            log.error("不是本人查询");
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
