package com.zw.sell.repository;

import com.zw.sell.entity.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    private String OPENID = "adsq1112";

    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("114231");
        orderMaster.setBuyerName("Tom");
        orderMaster.setBuyerAddress("4340 SunStreet");
        orderMaster.setBuyerAmount(new BigDecimal(144));
        orderMaster.setBuyerPhone("3212764970");
        orderMaster.setBuyerOpenid("adsq1112");

        OrderMaster result = orderMasterRepository.save(orderMaster);
        Assert.assertNotNull(result);
    }
    @Test
    public void findByBuyerOpenId() {
        // page is 0 based index, and size means how many content in one page.
        // if we have 5 contents, use(0,3) will have 3 contents, use (1,3) will have 2 contents
        PageRequest request = PageRequest.of(0,1);
        Page<OrderMaster> result = orderMasterRepository.findByBuyerOpenid(OPENID, request);
        Assert.assertNotEquals(0,result.getTotalElements());
    }

}