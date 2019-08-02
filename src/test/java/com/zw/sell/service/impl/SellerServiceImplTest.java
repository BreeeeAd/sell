package com.zw.sell.service.impl;

import com.zw.sell.entity.SellerInfo;
import com.zw.sell.service.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SellerServiceImplTest {

    @Autowired
    SellerService sellerService;

    private String OPENID = "1qaz";
    @Test
    public void findOneByOpenid() {
        SellerInfo res = sellerService.findOneByOpenid("1qaz");
        Assert.assertEquals(OPENID,res.getOpenid());
    }
}