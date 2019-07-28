package com.zw.sell.repository;

import com.zw.sell.entity.OrderDetail;
import org.hibernate.criterion.Order;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Test
    //@Transactional
    public void saveTest(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("858868");
        orderDetail.setOrderId("1133221");
        orderDetail.setProductIcon("http://xxxx.com");
        orderDetail.setProductId("133413");
        orderDetail.setProductName("Glasses");
        orderDetail.setProductPrice(new BigDecimal(299));
        orderDetail.setProductQuantity(1);

        OrderDetail result = orderDetailRepository.save(orderDetail);
        Assert.assertNotEquals(0,result);
    }

    @Test
    public void findByOrOrderId() {
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrOrderId("1133221");
        Assert.assertNotEquals(0,orderDetailList.size());
    }
}