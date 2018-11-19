package com.zx.sell.controller;

import com.zx.sell.Service.ProductCategoryService;
import com.zx.sell.Service.ProductInfoService;
import com.zx.sell.dataobject.ProductCategory;
import com.zx.sell.dataobject.ProductInfo;
import com.zx.sell.enums.ProductStatusEnum;
import com.zx.sell.exception.SellException;
import com.zx.sell.form.ProductForm;
import com.zx.sell.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "5") Integer size,
                             Map<String, Object> map) {
        Integer sumPage = 0;
        List<ProductInfo> productInfoList = productInfoService.getLimitProducts(size, page);
        List<ProductInfo> sumProductInfoList = productInfoService.getLimitProducts(999, 1);
        if (sumProductInfoList.size() % size == 0){
            sumPage = sumProductInfoList.size() / size;
        } else {
            sumPage = (sumProductInfoList.size() / size) + 1;
        }
//        阉割掉时间功能
//        for (ProductInfo productInfo : productInfoList){
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            productInfo.setCreateTime(simpleDateFormat.format(productInfo.getCreateTime()));
//        }
        System.out.println(productInfoList.toString());
        map.put("productList", productInfoList);
        map.put("sumPage", sumPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("product/list", map);
    }

    @RequestMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String, Object> map) {
        try {
//            productService.onSale(productId);
            productInfoService.onSale(productId);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }

        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }

    @RequestMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                                Map<String, Object> map) {
        try {
            productInfoService.offSale(productId);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }

        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }

    @RequestMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
                      Map<String, Object> map) {
        System.out.println(productId);
        if(!StringUtils.isEmpty(productId)){
            ProductInfo productInfo = productInfoService.getProductInfoByID(productId);
            map.put("productInfo", productInfo);
        }

        //查询所有的类目
        List<ProductCategory> categoryList = productCategoryService.getAllProductCates();
        map.put("categoryList", categoryList);

        return new ModelAndView("product/index", map);
    }

    /**
     * 保存及更新
     * @param form
     * @param bindingResult
     * @param map
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
//    @CachePut(cacheNames = "product", key = "123")
    @CacheEvict(cacheNames = "product", key = "123")
    public ModelAndView save(@Valid ProductForm form,
                             BindingResult bindingResult,
                             Map<String, Object> map) {

        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("common/error", map);
        }

        if (StringUtils.isEmpty(form.getProductId())){
            //新增
            ProductInfo productInfo = new ProductInfo();
            BeanUtils.copyProperties(form, productInfo);
            System.out.println(productInfo);
            productInfo.setProductId(KeyUtil.genUniqueKey());
            productInfo.setCreateTime(new Date());
            productInfo.setUpdateTime(new Date());
            productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
            try {
                productInfoService.addProductInfo(productInfo);
            } catch (SellException e){
                map.put("msg", e.getMessage());
                map.put("url", "/sell/seller/product/index");
                return new ModelAndView("common/error", map);
            }
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/success", map);
        } else {
            //修改
            ProductInfo productInfo = productInfoService.getProductInfoByID(form.getProductId());
            BeanUtils.copyProperties(form, productInfo);
            productInfo.setUpdateTime(new Date());
            try {
                productInfoService.updateProductInfo(productInfo);
            } catch (SellException e){
                map.put("msg", e.getMessage());
                map.put("url", "/sell/seller/product/index");
                return new ModelAndView("common/error", map);
            }

            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/success", map);
        }

    }
}
