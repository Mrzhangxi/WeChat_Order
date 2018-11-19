package com.zx.sell.dao;

import com.zx.sell.dataobject.ProductInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductInfoDao {

    /**
     * 获得所有产品信息
     * @return
     */
    List<ProductInfo> getAllProducts();

    /**
     * 根据ID获取产品
     * @return
     */
    ProductInfo getProductByID(String productID);

    /**
     * 根据产品状态获得产品
     * @param productStatus
     * @return
     */
    List<ProductInfo> getProductsByStatus(Integer productStatus);

    /**
     * 分页查询产品（这里更好的写法应该是添加一个搜索条件实体类，然后实现各种条件的分页查询）
     * @param startIndex
     * @param endIndex
     * @return
     */
    List<ProductInfo> getLimitProduct(@Param("startIndex") Integer startIndex, @Param("endIndex") Integer endIndex);

    /**
     * 添加新的产品
     * @param productInfo
     * @return
     */
    Integer addProduct(ProductInfo productInfo);

    /**
     * 修改已有的产品
     * @param productInfo
     * @return
     */
    Integer updateProduct(ProductInfo productInfo);

    /**
     * 删除以后的产品
     * @param productID
     * @return
     */
    Integer deleteProduct(String productID);


}
