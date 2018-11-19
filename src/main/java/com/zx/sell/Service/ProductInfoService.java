package com.zx.sell.Service;

import com.zx.sell.dataobject.ProductInfo;
import com.zx.sell.dto.CartDTO;

import java.util.List;

public interface ProductInfoService {

    /**
     * 根据ProductID获取ProductInfo对象
     * @param productID
     * @return
     */
    ProductInfo getProductInfoByID(String productID);

    /**
     * 根据产品状态获取ProductInfo, 0表示上架，1表示下架
     * @param Status 状态码
     * @return
     */
    List<ProductInfo> getAllUpProduct(Integer Status);

    /**
     * 分页查询产品
     * @param pagesize 页大小
     * @param pagenum 页数量
     * @return
     */
    List<ProductInfo> getLimitProducts(Integer pagesize, Integer pagenum);

    /**
     * 添加产品
     * @param productInfo
     * @return
     */
    Integer addProductInfo(ProductInfo productInfo);

    /**
     * 更新产品
     * @param productInfo
     * @return
     */
    Integer updateProductInfo(ProductInfo productInfo);

    /**
     * 根据购物车批量加库存
     * @param cartDTOList
     * @return
     */
    Integer increaseStock(List<CartDTO> cartDTOList);

    /**
     * 根据购物车批量减库存
     * @param cartDTOList
     * @return
     */
    Integer decreaseStock(List<CartDTO> cartDTOList);

    /**
     * 上架商品
     * @param productId
     * @return
     */
    ProductInfo onSale(String productId);

    /**
     * 下架商品
     * @param productId
     * @return
     */
    ProductInfo offSale(String productId);
}
