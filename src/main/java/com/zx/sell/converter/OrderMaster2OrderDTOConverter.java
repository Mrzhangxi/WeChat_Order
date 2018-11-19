package com.zx.sell.converter;

import com.zx.sell.dataobject.OrderMaster;
import com.zx.sell.dto.OrderDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderMaster2OrderDTOConverter {
    public static OrderDTO convert(OrderMaster orderMaster){
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList) {
        List<OrderDTO> orderDTOList = new ArrayList<OrderDTO>();
        for (OrderMaster orderMaster : orderMasterList){
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(orderMaster, orderDTO);
            orderDTOList.add(orderDTO);
        }
        return orderDTOList;
    }
}
