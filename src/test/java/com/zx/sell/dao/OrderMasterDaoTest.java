package com.zx.sell.dao;

import com.zx.sell.dataobject.OrderMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterDaoTest {

    @Autowired
    private OrderMasterDao orderMasterDao;

    @Test
    public void getOrderMasterByID() {
        OrderMaster orderMaster = orderMasterDao.getOrderMasterByID("dd0001");
        System.out.println(orderMaster.toString());
    }

    @Test
    public void updateOrderMaster() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("dd0001");
        orderMaster.setBuyerName("张曦");
        orderMaster.setBuyerPhone("18035823793");
        orderMaster.setBuyerAddress("山西省");
        orderMaster.setBuyerOpenid("111111111111");
        orderMaster.setOrderAmount(new BigDecimal("666.88"));
        orderMaster.setOrderStatus(1);
        orderMaster.setPayStatus(1);
        int eff = orderMasterDao.updateOrderMaster(orderMaster);
        assertEquals(1,eff);
    }

    @Test
    public void addOrderMaster() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("dd0001");
        orderMaster.setBuyerName("张曦");
        orderMaster.setBuyerPhone("18035823793");
        orderMaster.setBuyerAddress("山西省");
        orderMaster.setBuyerOpenid("111111111111");
        orderMaster.setOrderAmount(new BigDecimal("888.88"));
        orderMaster.setOrderStatus(0);
        orderMaster.setPayStatus(0);
        int eff = orderMasterDao.addOrderMaster(orderMaster);
        assertEquals(1,eff);
    }

    @Test
    public void getOrdersByBuyerOpenId() {
        List<OrderMaster> list = orderMasterDao.getOrdersByBuyerOpenId("123456", 0 ,2);
        System.out.println(list.toString());
        assertEquals(2, list.size());
    }
}