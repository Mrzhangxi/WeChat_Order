package com.zx.sell.dao;

import com.zx.sell.dataobject.ProductInfo;
import com.zx.sell.enums.ProductStatusEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoDaoTest {

    @Autowired
    private ProductInfoDao productInfoDao;

    @Test
    public void getAllProducts() {
        List<ProductInfo> list = productInfoDao.getAllProducts();
        System.out.println(list.toString());
    }

    @Test
    public void getProductByID(){
        ProductInfo productInfo = productInfoDao.getProductByID("p001");
        System.out.println(productInfo.toString());
    }

    @Test
    public void getProductsByStatus() {

        List<ProductInfo> list = productInfoDao.getProductsByStatus(ProductStatusEnum.UP.getCode());
        System.out.println(list.toString());
    }

    @Test
    public void getLimitProduct(){
        List<ProductInfo> list = productInfoDao.getLimitProduct(2,4);
        assertEquals(2, list.size());
    }

    @Test
    public void addProduct() {
        ProductInfo product = new ProductInfo();
        product.setProductId("p005");
        product.setProductName("测试产品5");
        product.setProductPrice(new BigDecimal("54.23"));
        product.setProductStock(20);
        product.setProductDescription("测试产品P005");
        product.setProductIcon("/");
        product.setProductStatus(1);
        product.setCategoryType(2);
        product.setCreateTime(new Date());
        product.setUpdateTime(new Date());
        int eff = productInfoDao.addProduct(product);
        assertEquals(1, eff);
    }

    @Test
    public void updateProduct() {
        ProductInfo product = new ProductInfo();
        product.setProductId("p005");
        product.setProductStock(50);
        product.setProductStatus(0);
        int eff = productInfoDao.updateProduct(product);
        assertEquals(1, eff);
    }

    @Test
    public void deleteProduct() {
        int eff = productInfoDao.deleteProduct("p005");
        assertEquals(1,eff);
    }
}