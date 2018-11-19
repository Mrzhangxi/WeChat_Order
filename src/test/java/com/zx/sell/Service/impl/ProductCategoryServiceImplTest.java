package com.zx.sell.Service.impl;

import com.zx.sell.Service.ProductCategoryService;
import com.zx.sell.dataobject.ProductCategory;
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
public class ProductCategoryServiceImplTest {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Test
    public void getProductCategoryByID() {

    }

    @Test
    public void getAllProductCates() {
    }

    @Test
    public void getProductCategorysByType() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        List<ProductCategory> alllist = productCategoryService.getProductCategorysByType(list);
        System.out.println(alllist);
    }

    @Test
    public void getgetProductCategorysByCategoryTypeCode(){
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        List<ProductCategory> alllist = productCategoryService.getProductCategorysByCategoryTypeCode(list);
        System.out.println(alllist);
    }

    @Test
    public void addProductCategory() {
    }
}