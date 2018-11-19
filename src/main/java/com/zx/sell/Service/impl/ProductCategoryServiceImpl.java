package com.zx.sell.Service.impl;

import com.zx.sell.Service.ProductCategoryService;
import com.zx.sell.dao.ProductCategoryDao;
import com.zx.sell.dataobject.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Override
    public ProductCategory getProductCategoryByID(Integer categoryID) {
        return productCategoryDao.getProductCategoryByID(categoryID);
    }

    @Override
    public List<ProductCategory> getAllProductCates() {
        return productCategoryDao.getAllProductCategorys();
    }

    @Override
    public List<ProductCategory> getProductCategorysByType(List<Integer> categoryTypeList) {
        List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
        for(Integer typeID:categoryTypeList){
            List<ProductCategory> tpcList = productCategoryDao.getProductCategorysByType(typeID);
            if (tpcList != null){
                for (ProductCategory pc:tpcList){
                    productCategoryList.add(pc);
                }
            }
        }
        return productCategoryList;
    }

    @Override
    public Integer addProductCategory(ProductCategory productCategory) {
        return productCategoryDao.addProductCategory(productCategory);
    }

    @Override
    public Integer updateProductCategory(ProductCategory productCategory) {
        return productCategoryDao.updateProductCategory(productCategory);
    }

    @Override
    public List<ProductCategory> getProductCategorysByCategoryTypeCode(List<Integer> typeCode) {
        List<ProductCategory> list = new ArrayList<ProductCategory>();
        for (Integer code : typeCode){
            List<ProductCategory> templist = productCategoryDao.getProductCategorysByType(code);
            if (templist != null && templist.size() > 0){
                list.add(templist.get(0));
            }
        }
        return list;
    }
}
