package com.zx.sell.dao;

import com.zx.sell.dataobject.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryDaoTest {
    @Autowired
    private ProductCategoryDao productCategoryDao;

//    @Test
//    public void findOneTest() {
//        ProductCategory pc = productCategoryDao.getallProductCategory();
//        System.out.println(pc.toString());
//    }

    @Test
    public void testGetAllProductCategory(){
        List<ProductCategory> productCategoryList = productCategoryDao.getAllProductCategorys();
        System.out.println(productCategoryList.get(0).toString());
        System.out.println(productCategoryList.get(1).toString());
    }

    @Test
    public void testGetProductCategoryByID(){
        ProductCategory productCategory = productCategoryDao.getProductCategoryByID(1);
        System.out.println(productCategory.toString());
    }

    @Test
    public void testInsertProductCategory(){
        ProductCategory productCategory = new ProductCategory();
//        productCategory.setCategoryId(3);
        productCategory.setCategoryName("测试");
        productCategory.setCategoryType(1);
        productCategory.setCreateTime(new Date());
        productCategory.setUpdateTime(new Date());
        int  eff = productCategoryDao.addProductCategory(productCategory);
        assertEquals(1, eff);
    }

    @Test
    public void testUpdateProductCategory(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryId(1);
        productCategory.setCategoryName("测试");
        productCategory.setCategoryType(1);
        productCategory.setUpdateTime(new Date());
        int eff = productCategoryDao.updateProductCategory(productCategory);
        assertEquals(1, eff);
    }

    @Test
    public void testDeleteProductCategory(){
        int eff = productCategoryDao.deleteProductCategory(2);
        assertEquals(1, eff);
    }

    @Test
    public void testGetProductCategorysByType(){
        List<ProductCategory> productCategoryList = productCategoryDao.getProductCategorysByType(1);
        System.out.println(productCategoryList);
    }
}