package com.zw.sell.service;

import com.zw.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    // create order
    OrderDTO create(OrderDTO orderDTO);

    // find order
    OrderDTO findOne(String orderId);

    // find order list by buyer Openid
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    // find order list
    Page<OrderDTO> findList(Pageable pageable);

    // cancel order
    OrderDTO cancel(OrderDTO orderDTO);

    // finish order
    OrderDTO finish(OrderDTO orderDTO);

    // pay order
    OrderDTO pay(OrderDTO orderDTO);
}
