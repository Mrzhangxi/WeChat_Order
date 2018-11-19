package com.zx.sell.dao;

import com.zx.sell.dataobject.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailDao {
    /**
     * 根据OrderMaster编号去寻找OrderDetail的list
     * @param orderId
     * @return
     */
    List<OrderDetail> getOrderDetailsByOrderID(String orderId);

    OrderDetail getOrderDetailByID(String detailId);

    Integer addOrderDetail(OrderDetail orderDetail);

    Integer addOrderDetails(List<OrderDetail> orderDetails);

    Integer updateOrderDetail(OrderDetail orderDetail);

}
