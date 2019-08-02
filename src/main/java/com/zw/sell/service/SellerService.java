package com.zw.sell.service;

import com.zw.sell.entity.SellerInfo;

public interface SellerService {
    SellerInfo findOneByOpenid(String openid);
}
