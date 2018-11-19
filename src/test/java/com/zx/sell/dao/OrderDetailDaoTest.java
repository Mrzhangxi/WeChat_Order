package com.zx.sell.dao;

import com.zx.sell.dataobject.OrderDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailDaoTest {

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Test
    public void getOrderDetailsByOrderID() {
        List<OrderDetail> orderDetails = orderDetailDao.getOrderDetailsByOrderID("dd0001");
        System.out.println(orderDetails.toString());
    }

    @Test
    public void getOrderDetailByID() {
        OrderDetail orderDetail = orderDetailDao.getOrderDetailByID("od00001");
        System.out.println(orderDetail.toString());
    }

    @Test
    public void addOrderDetail() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("od00002");
        orderDetail.setOrderId("dd0001");
        orderDetail.setProductId("p001");
        orderDetail.setProductName("测试产品1");
        orderDetail.setProductPrice(new BigDecimal("10"));
        orderDetail.setProductQuantity(2);
        orderDetail.setProductIcon("/");
        orderDetail.setCreateTime(new Date());
        orderDetail.setUpdateTime(new Date());
        int eff = orderDetailDao.addOrderDetail(orderDetail);
        assertEquals(1, eff);
    }

    @Test
    public void addOrderDetails() {
        OrderDetail detail1 = new OrderDetail();
        detail1.setDetailId("od00003");
        detail1.setOrderId("dd0002");
        detail1.setProductId("p002");
        detail1.setProductName("测试产品2");
        detail1.setProductPrice(new BigDecimal("10"));
        detail1.setProductQuantity(3);
        detail1.setProductIcon("/");
        detail1.setCreateTime(new Date());
        detail1.setUpdateTime(new Date());
        OrderDetail detail2 = new OrderDetail();
        detail2.setDetailId("od00004");
        detail2.setOrderId("dd0002");
        detail2.setProductId("p002");
        detail2.setProductName("测试产品2");
        detail2.setProductPrice(new BigDecimal("10"));
        detail2.setProductQuantity(3);
        detail2.setProductIcon("/");
        detail2.setCreateTime(new Date());
        detail2.setUpdateTime(new Date());
        List<OrderDetail> list = new ArrayList<OrderDetail>();
        list.add(detail1);
        list.add(detail2);
        int eff = orderDetailDao.addOrderDetails(list);
        assertEquals(2, eff);
    }

    @Test
    public void updateOrderDetail() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("od00001");
        orderDetail.setOrderId("dd0001");
        orderDetail.setProductId("p002");
        orderDetail.setProductName("测试产品2");
        orderDetail.setProductPrice(new BigDecimal("10"));
        orderDetail.setProductQuantity(3);
        orderDetail.setProductIcon("/");
        orderDetail.setCreateTime(new Date());
        orderDetail.setUpdateTime(new Date());
        int eff = orderDetailDao.updateOrderDetail(orderDetail);
        assertEquals(1, eff);
    }
}