package com.zx.sell.Service;

import com.zx.sell.dataobject.OrderMaster;
import com.zx.sell.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    //创建订单
    OrderDTO create(OrderDTO orderDTO);

    //查询订单
    OrderDTO getOrderDTOByID(String orderId);
    //查询订单列表
    List<OrderDTO> getOrderDTOsByBuyer(String buyerOpenId, Integer pageSize, Integer pageNum);
    //查询订单列表
    List<OrderDTO> getOrderDTOs(Integer pageSize, Integer pageNum);

    //取消订单
    OrderDTO cancel(OrderDTO orderDTO);
    //完结订单
    OrderDTO finish(OrderDTO orderDTO);
    //支付订单
    OrderDTO paid(OrderDTO orderDTO);
}
