package com.zx.sell.Service.impl;

import com.zx.sell.Service.ProductInfoService;
import com.zx.sell.dataobject.ProductInfo;
import com.zx.sell.dto.CartDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoService productInfoService;

    @Test
    public void getProductInfoByID() {
    }

    @Test
    public void getAllUpProduct() {
    }

    @Test
    public void getLimitProducts() {
        List<ProductInfo> list = productInfoService.getLimitProducts(2,1);
        assertEquals(2, list.size());
        System.out.println(list.toString());
    }

    @Test
    public void addProductInfo() {
    }

    @Test
    public void productNumIncrease() {
        List<CartDTO> cartDTOList = new ArrayList<CartDTO>();
        cartDTOList.add(new CartDTO("p005", 2));
        cartDTOList.add(new CartDTO("p003", 1));
        Integer eff = productInfoService.increaseStock(cartDTOList);
        assertEquals(2,2);
    }

    @Test
    public void productNumdecrease() {
    }
}