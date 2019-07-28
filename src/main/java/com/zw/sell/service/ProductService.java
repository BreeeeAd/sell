package com.zw.sell.service;

import com.zw.sell.dto.CartDTO;
import com.zw.sell.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductInfo findOne(String productId);

    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    // increase stock
    void increaseStock(List<CartDTO> cartDTOList);

    // decrease stock
    void decreaseStock(List<CartDTO> cartDTOList);

    // on sale
    ProductInfo onSale(String productId);

    // off sale
    ProductInfo offSale(String productId);

}
