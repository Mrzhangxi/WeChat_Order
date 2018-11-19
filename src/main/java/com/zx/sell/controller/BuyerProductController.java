package com.zx.sell.controller;

import com.zx.sell.Service.ProductCategoryService;
import com.zx.sell.Service.ProductInfoService;
import com.zx.sell.ViewObject.ProductInfoVO;
import com.zx.sell.ViewObject.ProductVO;
import com.zx.sell.ViewObject.ResultVO;
import com.zx.sell.dataobject.ProductCategory;
import com.zx.sell.dataobject.ProductInfo;
import com.zx.sell.enums.ProductStatusEnum;
import com.zx.sell.utils.ResultVOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 卖家商品
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping("/list")
//    缓存
    @Cacheable(cacheNames = "product", key = "123")
    public ResultVO list(){

        //查询所有的上架商品
        List<ProductInfo> productInfoList = productInfoService.getAllUpProduct(ProductStatusEnum.UP.getCode());
        List<Integer> typeCodeList = new ArrayList<Integer>();

        for (ProductInfo product:productInfoList){
            if (typeCodeList.indexOf(product.getCategoryType()) <= 0){
                typeCodeList.add(product.getCategoryType());
            }
        }
        System.out.println(typeCodeList);
        //查询类目（一次性查询）
        List<ProductCategory> productCategoryList = productCategoryService.getProductCategorysByCategoryTypeCode(typeCodeList);//分类列表
        //数据拼装
        List<ProductVO> productVOList = new ArrayList<ProductVO>();//最外层的DataList，子元素是分类

        System.out.println(productCategoryList + "---------------------");

        for(ProductCategory productCategory : productCategoryList){
            ProductVO productVO = new ProductVO();//productVOList的子元素
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());
            List<ProductInfoVO> productInfoVOList = new ArrayList<ProductInfoVO>();
            for (ProductInfo productInfo : productInfoList) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

        return ResultVOUtils.success(productVOList);
    }
}
