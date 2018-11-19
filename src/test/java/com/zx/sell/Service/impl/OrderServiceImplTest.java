package com.zx.sell.Service.impl;

import com.zx.sell.Service.OrderService;
import com.zx.sell.dataobject.OrderDetail;
import com.zx.sell.dto.OrderDTO;
import com.zx.sell.enums.OrderStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    private final String BUYER_OPENID = "123456";
    private final String ORDER_ID = "om1541945794402743991";

    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("张曦");
        orderDTO.setBuyerAddress("大同市");
        orderDTO.setBuyerPhone("18035823793");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("p003");
        o1.setProductQuantity(1);

        OrderDetail o2 = new OrderDetail();
        o2.setProductId("p005");
        o2.setProductQuantity(2);

        orderDetailList.add(o1);
        orderDetailList.add(o2);

        orderDTO.setOrderDetailList(orderDetailList);
        OrderDTO result = orderService.create(orderDTO);
//        log.info("【创建订单】result={}", result);
        Assert.assertNotNull(result);
    }

    @Test
    public void getOrderDTOByID() {
        OrderDTO result = orderService.getOrderDTOByID(ORDER_ID);
        System.out.println(result.toString());
        Assert.assertEquals(ORDER_ID, result.getOrderId());
    }

    @Test
    public void getOrderDTOsByBuyer() {
        List<OrderDTO> orderDTOList = orderService.getOrderDTOsByBuyer("123456", 2,1);
        System.out.println(orderDTOList.toString());
    }

    @Test
    public void getOrderDTOs() {
        List<OrderDTO> orderDTOList = orderService.getOrderDTOs(10,1);
        System.out.println(orderDTOList.toString());
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderService.getOrderDTOByID(ORDER_ID);
        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertNotEquals(OrderStatusEnum.CANCEL.getCode(), result.getOrderStatus());
    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderService.getOrderDTOByID("om1542005945458817132");
        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(), orderDTO.getOrderStatus());
    }

    @Test
    public void paid() {
        OrderDTO orderDTO = orderService.getOrderDTOByID("om1542005945458817132");
        OrderDTO result = orderService.paid(orderDTO);
    }
}