package com.zw.sell.service.impl;

import com.zw.sell.converter.OrderMasterToOrderDTO;
import com.zw.sell.dto.CartDTO;
import com.zw.sell.dto.OrderDTO;
import com.zw.sell.entity.OrderDetail;
import com.zw.sell.entity.OrderMaster;
import com.zw.sell.entity.ProductInfo;
import com.zw.sell.enums.OrderStatusEnums;
import com.zw.sell.enums.PayStatusEnums;
import com.zw.sell.enums.ResultEnum;
import com.zw.sell.exception.SellException;
import com.zw.sell.repository.OrderDetailRepository;
import com.zw.sell.repository.OrderMasterRepository;
import com.zw.sell.service.OrderService;
import com.zw.sell.service.ProductService;
import com.zw.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        // Input Example:
        // productId: "1415123412351235"
        // productQuantity: 2

        String orderId = KeyUtil.genUniqueKey();

        BigDecimal totalAmount = new BigDecimal(0);

        // 1. check the price and quantity of product
        for(OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            // 2. calculate the total price
            totalAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal (orderDetail.getProductQuantity()))
                    .add(totalAmount);

            // create order detail
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            // copy( from, to)
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetailRepository.save(orderDetail);
        }

        // 3. create and insert order into order table
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setBuyerAmount(totalAmount);
        orderMaster.setOrderStatus(OrderStatusEnums.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnums.WAIT.getCode());
        orderMasterRepository.save(orderMaster);


        // 4. update the product stock
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).orElse(null);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrOrderId(orderId);
        if (orderDetailList.isEmpty()){
            throw new SellException(ResultEnum.ORDER_DETAILS_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid,pageable);

        List<OrderDTO>  orderDTOList = OrderMasterToOrderDTO.convert(orderMasterPage.getContent());

        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);

        List<OrderDTO> orderDTOList = OrderMasterToOrderDTO.convert(orderMasterPage.getContent());

        Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();

        // check the status of order
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())) {
            log.error("[CANCEL ORDER] order status incorrect, orderId = {}, orderStatus = {}",
                    orderDTO.getOrderId(), orderDTO.getOrderStatusEnum());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        
        // change order's status
        orderDTO.setOrderStatus(OrderStatusEnums.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updatedResult = orderMasterRepository.save(orderMaster);
        if (updatedResult == null) {
            log.error("[CANCEL ORDER] failed to cancel order {}", orderMaster);
        }

        // change the product's stock
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("[CANCEL ORDER] no detail list in order, orderDTO = {}", orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(),e.getProductQuantity()) )
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);
        // if the order is already paid, refund to buyer
        if(orderMaster.getPayStatus().equals(PayStatusEnums.SUCCESS.getCode())){
            //TODO
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();

        // check the order status
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())){
            log.error("[FINISH ORDER] order status incorrect orderDTO = {}", orderDTO);
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // change the order status
        orderDTO.setOrderStatus(OrderStatusEnums.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if(result == null) {
            log.error("[FINISH ORDER] order update error {}", orderMaster);
            throw new SellException(ResultEnum.ORDER_DETAILS_NOT_EXIST);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO pay(OrderDTO orderDTO) {
        // find the order
        // check the order status
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())){
            log.error("[PAY ORDER] order status incorrect");
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        if (!orderDTO.getPayStatus().equals(PayStatusEnums.WAIT.getCode())){
            log.error("[PAY ORDER] cant pay order");
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        // change the order pay status
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setPayStatus(PayStatusEnums.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if (result == null) {
            log.error("[PAY ORDER] ");
            throw new SellException(ResultEnum.ORDER_UPDATED_FAIL);
        }
        return orderDTO;
    }
}
