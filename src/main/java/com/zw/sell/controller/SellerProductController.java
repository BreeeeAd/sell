package com.zw.sell.controller;

import com.zw.sell.dto.OrderDTO;
import com.zw.sell.entity.ProductCategory;
import com.zw.sell.entity.ProductInfo;
import com.zw.sell.enums.ResultEnum;
import com.zw.sell.exception.SellException;
import com.zw.sell.form.ProductForm;
import com.zw.sell.service.CategoryService;
import com.zw.sell.service.OrderService;
import com.zw.sell.service.ProductService;
import com.zw.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10") Integer size,
                             Map<String, Object> map){
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<ProductInfo> productInfoPage = productService.findAll(pageRequest);
        map.put("productInfoPage", productInfoPage);
        map.put("currentPage", page);
        map.put("currentSize", size);
        return new ModelAndView("product/list", map);

    }

    @RequestMapping(value = "/off_sale", method = RequestMethod.GET)
    public ModelAndView offSale(@RequestParam("productId") String productId,
                                Map<String, Object> map){
        try {
            ProductInfo productInfo = productService.offSale(productId);
        } catch (SellException e){
            log.error("[PRODUCT ACTION] can't change product status");
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("/common/error",  map);
        }
        map.put("msg", ResultEnum.PRODUCT_STATUS_CHANGE_SUCCESS.getMsg());
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("/common/success");
    }

    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                                Map<String, Object> map){
        try {
            ProductInfo productInfo = productService.onSale(productId);
        } catch (SellException e){
            log.error("[PRODUCT ACTION] can't change product status");
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("/common/error",  map);
        }
        map.put("msg", ResultEnum.PRODUCT_STATUS_CHANGE_SUCCESS.getMsg());
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("/common/success");
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
                              Map<String, Object> map){
        if (productId != null){
            ProductInfo productInfo = productService.findOne(productId);
            map.put("productInfo", productInfo);
        }

        //find all category
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList", categoryList);

        return new ModelAndView("product/index", map);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm form,
                             BindingResult bindingResult,
                             Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("common/error", map);
        }
        ProductInfo productInfo = new ProductInfo();
        try {
            if (form.getProductId()== null) {
                productInfo = productService.findOne(form.getProductId());
            } else {
                form.setProductId(KeyUtil.genUniqueKey());
            }
            BeanUtils.copyProperties(form, productInfo);
            productService.save(productInfo);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("common/error", map);
        }

        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }
}
