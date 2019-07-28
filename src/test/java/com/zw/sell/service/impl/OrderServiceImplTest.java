package com.zw.sell.service.impl;

import com.zw.sell.dto.OrderDTO;
import com.zw.sell.entity.OrderDetail;
import com.zw.sell.enums.OrderStatusEnums;
import com.zw.sell.enums.PayStatusEnums;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private final String BUYER_OPENID = "110110";

    private final String ORDER_ID = "1563397261750162938";


    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO("Tom"
                ,"13322329999"
                , "21355 Street"
                ,BUYER_OPENID);

        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail("123456", 1);
        OrderDetail o2 = new OrderDetail("113322",3);
        orderDetailList.add(o1);
        orderDetailList.add(o2);
        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.create(orderDTO);
        log.info("Create order result = {}", result);


    }

    @Test
    public void findOne() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO = orderService.findOne(ORDER_ID);
        log.info("find by order id result = {}", orderDTO);
        Assert.assertNotEquals(0,orderDTO.getOrderId());
    }

    @Test
    public void findList() {
        Pageable pageable = PageRequest.of(0,3);
        Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID, pageable);
        Assert.assertNotEquals(0,orderDTOPage);
    }

    @Test
    public void list(){
        Pageable pageable = PageRequest.of(0,3);
        Page<OrderDTO> orderDTOPage = orderService.findList(pageable);
//        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
        Assert.assertTrue("find the list of all orders", orderDTOPage.getTotalElements() > 0);
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnums.CANCEL.getCode(),orderDTO.getOrderStatusEnum());
    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnums.FINISHED.getCode(),orderDTO.getOrderStatusEnum());
    }

    @Test
    public void pay() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.pay(orderDTO);
        Assert.assertEquals(PayStatusEnums.SUCCESS.getCode(),result.getPayStatus());
    }
}