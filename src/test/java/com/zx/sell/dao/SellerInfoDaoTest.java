package com.zx.sell.dao;

import com.zx.sell.dataobject.SellerInfo;
import com.zx.sell.utils.KeyUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoDaoTest {

    @Autowired
    private SellerInfoDao sellerInfoDao;

    @Test
    public void getSellerByopenId() {
        SellerInfo sellerInfo = sellerInfoDao.getSellerByopenId("123456");
        System.out.println(sellerInfo.getSellerId());
    }

    @Test
    public void getSellerByID() {
        System.out.println(sellerInfoDao.getSellerByID("1542419439981409737"));
    }

    @Test
    public void addSeller() {
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(KeyUtil.genUniqueKey());
        sellerInfo.setUsername("张曦");
        sellerInfo.setPassword("123654147");
        sellerInfo.setOpenid("123456");
        sellerInfo.setCreateTime(new Date());
        sellerInfo.setUpdateTime(new Date());
        int eff = sellerInfoDao.addSeller(sellerInfo);
        assertEquals(1, eff);
    }

    @Test
    public void updateSeller() {
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId("1542419439981409737");
        sellerInfo.setUsername("张曦");
        sellerInfo.setPassword("123654147");
        sellerInfo.setOpenid("123456");
        sellerInfo.setCreateTime(new Date());
        sellerInfo.setUpdateTime(new Date());
        int eff = sellerInfoDao.updateSeller(sellerInfo);
        assertEquals(1, eff);
    }
}