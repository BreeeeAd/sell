package com.zw.sell.repository;

import com.zw.sell.entity.SellerInfo;
import com.zw.sell.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepositoryTest {

    @Autowired
    SellerInfoRepository sellerInfoRepository;

    String OPENID = "1qaz";

    @Test
    public void save(){
        SellerInfo sellerInfo = new SellerInfo();

        sellerInfo.setSellerId(KeyUtil.genUniqueKey());
        sellerInfo.setOpenid(OPENID);
        sellerInfo.setUsername("admin");
        sellerInfo.setPassword("admin");

        SellerInfo result =  sellerInfoRepository.save(sellerInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOpenid() {
        SellerInfo sellerInfo = sellerInfoRepository.findByOpenid(OPENID);
        Assert.assertNotEquals(null,sellerInfo);
    }
}