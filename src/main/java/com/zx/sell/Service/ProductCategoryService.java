package com.zx.sell.Service;

import com.zx.sell.dataobject.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    /**
     * 通过ID获取分类
     * @param categoryID
     * @return
     */
    ProductCategory getProductCategoryByID(Integer categoryID);

    /**
     * 获得所有的分类
     * @return
     */
    List<ProductCategory> getAllProductCates();

    /**
     * 通过Type获取对应的分类
     * @param categoryTypeList
     * @return
     */
    List<ProductCategory> getProductCategorysByType(List<Integer> categoryTypeList);

    /**
     * 添加新的ProductCategory
     * @param productCategory
     * @return
     */
    Integer addProductCategory(ProductCategory productCategory);

    /**
     * 修改ProductCategory
     * @param productCategory
     * @return
     */
    Integer updateProductCategory(ProductCategory productCategory);

    /**
     * 根据传入的Ingter数组获取相应的产品分类List
     * @param typeCode
     * @return
     */
    List<ProductCategory> getProductCategorysByCategoryTypeCode(List<Integer> typeCode);
}
