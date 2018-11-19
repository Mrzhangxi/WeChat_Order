package com.zx.sell.dao;

import com.zx.sell.dataobject.OrderMaster;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMasterDao {

    //根据用户ID分页获取订单
    List<OrderMaster> getOrdersByBuyerOpenId(@Param("buyerOpenId") String buyerOpenId, @Param("startIndex") Integer startIndex, @Param("endIndex") Integer endIndex);

    List<OrderMaster> getOrders(@Param("startIndex") Integer startIndex, @Param("endIndex") Integer endIndex);

    OrderMaster getOrderMasterByID(String orderId);

    Integer updateOrderMaster(OrderMaster orderMaster);

    Integer addOrderMaster(OrderMaster orderMaster);


}
