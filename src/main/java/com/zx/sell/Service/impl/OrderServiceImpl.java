package com.zx.sell.Service.impl;

import com.zx.sell.Service.OrderService;
import com.zx.sell.Service.ProductInfoService;
import com.zx.sell.Service.WebSocket;
import com.zx.sell.converter.OrderMaster2OrderDTOConverter;
import com.zx.sell.dao.OrderDetailDao;
import com.zx.sell.dao.OrderMasterDao;
import com.zx.sell.dataobject.OrderDetail;
import com.zx.sell.dataobject.OrderMaster;
import com.zx.sell.dataobject.ProductInfo;
import com.zx.sell.dto.CartDTO;
import com.zx.sell.dto.OrderDTO;
import com.zx.sell.enums.OrderStatusEnum;
import com.zx.sell.enums.PayStatusEnum;
import com.zx.sell.enums.ResultEnum;
import com.zx.sell.exception.SellException;
import com.zx.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {


    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private OrderDetailDao orderDetailDao;
    @Autowired
    private OrderMasterDao orderMasterDao;
    @Autowired
    private WebSocket webSocket;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        String orderID = "om" + KeyUtil.genUniqueKey();//生成OrderMaster订单的ID
        BigDecimal orderAmount = new BigDecimal("0");//定义总价
        List<CartDTO> cartDTOList = new ArrayList<CartDTO>();

        //1.查询商品（数量、价格）
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            ProductInfo product = productInfoService.getProductInfoByID(orderDetail.getProductId());
            if(product == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2.计算总价
            //计算总价：==>>  Amount += price * quantity
            orderAmount = product.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);
            //3.1订单详情入库(Detail)
            orderDetail.setDetailId("od" + KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderID);
            BeanUtils.copyProperties(product, orderDetail);//Spring自带的工具类，将product对象的属性copy到orderDetail对象中
            orderDetailDao.addOrderDetail(orderDetail);
            //添加购物车list
            cartDTOList.add(new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity()));
        }

        //3.2写入订单数据库（Master）
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderId(orderID);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setCreateTime(new Date());
        orderMaster.setUpdateTime(new Date());
        orderMasterDao.addOrderMaster(orderMaster);

        //4.扣库存
        productInfoService.decreaseStock(cartDTOList);

        //将订单ID添加到orderDTO中
        List<OrderMaster> orderMasterList = orderMasterDao.getOrdersByBuyerOpenId(orderMaster.getBuyerOpenid(), 0, 999);//此处有待优化，默认个人订单记录数不会超过999
        orderDTO.setOrderId(orderMasterList.get(orderMasterList.size()-1).getOrderId());

        //5.通过WebSocket发送消息
        //TODO Websotket
//        webSocket.sendMessage("有新的订单");
        webSocket.sendMessage(orderDTO.getOrderId());
        return orderDTO;
    }

    @Override
    public OrderDTO getOrderDTOByID(String orderId) {
        OrderMaster orderMaster = orderMasterDao.getOrderMasterByID(orderId);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetailList = orderDetailDao.getOrderDetailsByOrderID(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

    @Override
    public List<OrderDTO> getOrderDTOsByBuyer(String buyerOpenId, Integer pageSize, Integer pageNum) {
        List<OrderMaster> orderMasterList = orderMasterDao.getOrdersByBuyerOpenId(buyerOpenId, pageSize*(pageNum-1), pageNum*pageSize);
        if (orderMasterList == null || orderMasterList.size() <= 0){
            return null;
        }
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterList);
        for (OrderDTO orderDTO:orderDTOList){
            orderDTO.setOrderDetailList(orderDetailDao.getOrderDetailsByOrderID(orderDTO.getOrderId()));
        }
        return orderDTOList;
    }

    public List<OrderDTO> getOrderDTOs(Integer pageSize, Integer pageNum) {
        List<OrderMaster> orderMasterList = orderMasterDao.getOrders(pageSize*(pageNum-1), pageNum*pageSize);
        if (orderMasterList == null || orderMasterList.size() <= 0){
            return null;
        }
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterList);
        for (OrderDTO orderDTO:orderDTOList){
            orderDTO.setOrderDetailList(orderDetailDao.getOrderDetailsByOrderID(orderDTO.getOrderId()));
        }
        return orderDTOList;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);

        //判断订单状态（接单，完结状态不可取消）
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
//            log.error("取消订单")
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        Integer eff = orderMasterDao.updateOrderMaster(orderMaster);
        if (eff<= 0){
//            log.error("更新失败");
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //返还库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
//            log.error("取消的订单中无商品详情");
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = new ArrayList<CartDTO>();
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            cartDTOList.add(new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity()));
        }
        productInfoService.increaseStock(cartDTOList);

        //如果已支付，退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            //TODO
//            payService.refund(orderDTO);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
//            log.error("【完结订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        Integer eff = orderMasterDao.updateOrderMaster(orderMaster);
        if (eff <= 0) {
//            log.error("【完结订单】更新失败, orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //判断支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
//            log.error("订单支付状态不正确");
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        //修改支付状态
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        Integer eff = orderMasterDao.updateOrderMaster(orderMaster);
        if (eff <= 0 ){
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }
}
