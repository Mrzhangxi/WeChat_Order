package com.zx.sell.Service;

import com.zx.sell.dto.OrderDTO;

//买家Service
public interface BuyerService {

    //查询一个订单
    OrderDTO getBuyerOrderByOrderID(String openid, String orderid);

    //取消订单
    OrderDTO cancelOrder(String openid, String orderId);
}
