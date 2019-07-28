package com.zw.sell.repository;

import com.zw.sell.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Jpa 输入的内容是主键以及主键的数据类型
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {
    List<ProductInfo> findByProductStatus (Integer productStatus);
}
