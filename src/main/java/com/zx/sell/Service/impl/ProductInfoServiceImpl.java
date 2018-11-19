package com.zx.sell.Service.impl;

import com.zx.sell.Service.ProductInfoService;
import com.zx.sell.dao.ProductInfoDao;
import com.zx.sell.dataobject.ProductInfo;
import com.zx.sell.dto.CartDTO;
import com.zx.sell.enums.ProductStatusEnum;
import com.zx.sell.enums.ResultEnum;
import com.zx.sell.exception.SellException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoDao productInfoDao;

    @Override
    public ProductInfo getProductInfoByID(String productID) {
        return productInfoDao.getProductByID(productID);
    }

    @Override
    public List<ProductInfo> getAllUpProduct(Integer Status) {
        return productInfoDao.getProductsByStatus(0);
    }

    @Override
    public List<ProductInfo> getLimitProducts(Integer pagesize, Integer pagenum) {
        //数据从0页开始
        return productInfoDao.getLimitProduct(pagesize*(pagenum-1), pagesize*pagenum);
    }

    @Override
    public Integer addProductInfo(ProductInfo productInfo) {
        return productInfoDao.addProduct(productInfo);
    }

    @Override
    public Integer updateProductInfo(ProductInfo productInfo) {
        return productInfoDao.updateProduct(productInfo);
    }

    @Override
    @Transactional
    public Integer increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList){
            ProductInfo productInfo = productInfoDao.getProductByID(cartDTO.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer reuslt = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(reuslt);
            System.out.println(productInfo.toString());
            productInfoDao.updateProduct(productInfo);
        }
        return null;
    }

    @Override
    @Transactional
    public Integer decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList){
            ProductInfo productInfo = productInfoDao.getProductByID(cartDTO.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer reuslt = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (reuslt < 0) {
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            } else {
                productInfo.setProductStock(reuslt);
                System.out.println(productInfo.toString());
                productInfoDao.updateProduct(productInfo);
            }
        }
        return null;
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = productInfoDao.getProductByID(productId);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfoDao.updateProduct(productInfo);
        return productInfoDao.getProductByID(productId);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = productInfoDao.getProductByID(productId);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfoDao.updateProduct(productInfo);
        return productInfoDao.getProductByID(productId);
    }


}
