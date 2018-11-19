package com.zx.sell.dao;

import com.zx.sell.dataobject.ProductCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductCategoryDao{

    /**
     * 获得所有产品分类
     * @return 产品分类List
     */
    List<ProductCategory> getAllProductCategorys();

    /**
     * 根据ID获取对应的产品分类
     * @return 产品分类
     */
    ProductCategory getProductCategoryByID(int productCategoryID);

    /**
     * 添加新的产品分类
     * @param pc
     * @return
     */
    int addProductCategory(ProductCategory pc);

    /**
     * 修改已有的分类
     * @param productCategory
     * @return
     */
    int updateProductCategory(ProductCategory productCategory);

    /**
     * 删除已有的分类
     * @param categoryID
     * @return
     */
    int deleteProductCategory(int categoryID);

    /**
     * 通过分类的Type来获取分类
     * @param typeID
     * @return
     */
    List<ProductCategory> getProductCategorysByType(Integer typeID);

//    ProductCategory getallProductCategory();
}
