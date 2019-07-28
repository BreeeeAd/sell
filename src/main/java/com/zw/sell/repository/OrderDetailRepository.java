package com.zw.sell.repository;

import com.zw.sell.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail , String> {

    List<OrderDetail> findByOrOrderId(String orderId);
}
