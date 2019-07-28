package com.zw.sell.service;

import com.zw.sell.dto.OrderDTO;
import org.springframework.stereotype.Service;

public interface BuyerService {

    // find order
    OrderDTO findOneOrderByOpenid(String openid, String orderId);


    // cancel order
    OrderDTO cancelOrderByOpenid(String openid, String orderId);
}
