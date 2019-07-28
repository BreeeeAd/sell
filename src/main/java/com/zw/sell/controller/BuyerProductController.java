package com.zw.sell.controller;

import com.zw.sell.ViewObject.ProductInfoVO;
import com.zw.sell.ViewObject.ProductVO;
import com.zw.sell.ViewObject.ResultVO;
import com.zw.sell.entity.ProductCategory;
import com.zw.sell.entity.ProductInfo;
import com.zw.sell.service.CategoryService;
import com.zw.sell.service.ProductService;
import com.zw.sell.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
//url syntax package name/ object name/
public class BuyerProductController {

//    @Autowired
    private CategoryService categoryService;


//    @Autowired
    private ProductService productService;

    @Autowired
    public BuyerProductController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/list")
    public ResultVO list(){

        // func 1. find all on stock products;
        List<ProductInfo> productInfoList = productService.findUpAll();


        // func 2. find all same category products;
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(ProductInfo::getCategoryType)
                .collect(Collectors.toList());
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        /*
         Java 8 new feature
         same as for()
                for (ProductInfo productInfo: productInfoList){
                    categoryTypeList.add(productInfo.getCategoryType());
                }

        */
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList){
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOS(productInfoVOList);
            productVOList.add(productVO);
        }

        return ResultVOUtil.success(productVOList);
    }
}
