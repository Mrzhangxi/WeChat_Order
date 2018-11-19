package com.zx.sell.Service.impl;

import com.zx.sell.Service.SellerService;
import com.zx.sell.dataobject.SellerInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerServiceImplTest {

    @Autowired
    private SellerService sellerService;

    @Test
    public void getSellerInfoByOpenId() {
        SellerInfo sellerInfo = sellerService.getSellerInfoByOpenId("123456");
        System.out.println(sellerInfo);
    }
}