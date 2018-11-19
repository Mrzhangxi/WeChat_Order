package com.zx.sell.controller;

import com.zx.sell.Service.ProductCategoryService;
import com.zx.sell.dataobject.ProductCategory;
import com.zx.sell.exception.SellException;
import com.zx.sell.form.CategoryForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 类目列表
     * @param map
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(Map<String, Object> map){
        List<ProductCategory> productCategoryList = productCategoryService.getAllProductCates();
        map.put("categoryList", productCategoryList);
        return new ModelAndView("category/list", map);
    }

    /**
     * 新增/修改
     * @param categoryId
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                              Map<String, Object> map){
        if (!StringUtils.isEmpty(categoryId) && categoryId > 0){
            ProductCategory productCategory = productCategoryService.getProductCategoryByID(categoryId);
            map.put("productCategory", productCategory);
        }

        return new ModelAndView("category/index", map);
    }

    @RequestMapping("/save")
    public ModelAndView save(@Valid CategoryForm form,
                             BindingResult bindingResult,
                             Map<String, Object> map){
        if (bindingResult.hasErrors()){
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/category/index");
            return new ModelAndView("common/error", map);
        }

        if (form.getCategoryId() != null) {
            //修改
            ProductCategory productCategory = productCategoryService.getProductCategoryByID(form.getCategoryId());
            BeanUtils.copyProperties(form, productCategory);
            System.out.println(productCategory);
            try {
                productCategoryService.updateProductCategory(productCategory);
            } catch (SellException e){
                map.put("msg", e.getMessage());
                map.put("url", "/sell/seller/category/index");
                return new ModelAndView("common/error", map);
            }
            map.put("url", "/sell/seller/category/list");
            return new ModelAndView("common/success", map);
        } else {
            //新增
            ProductCategory productCategory = new ProductCategory();
            BeanUtils.copyProperties(form, productCategory);
            System.out.println(productCategory);
            try {
                productCategoryService.addProductCategory(productCategory);
            } catch (SellException e){
                map.put("msg", e.getMessage());
                map.put("url", "/sell/seller/category/index");
                return new ModelAndView("common/error", map);
            }
            map.put("url", "/sell/seller/category/list");
            return new ModelAndView("common/success", map);
        }
    }
}
