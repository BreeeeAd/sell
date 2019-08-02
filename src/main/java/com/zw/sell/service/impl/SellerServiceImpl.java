package com.zw.sell.service.impl;

import com.zw.sell.entity.SellerInfo;
import com.zw.sell.repository.SellerInfoRepository;
import com.zw.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Override
    public SellerInfo findOneByOpenid(String openid) {
        SellerInfo sellerInfo = sellerInfoRepository.findByOpenid(openid);
        return sellerInfo;
    }
}
