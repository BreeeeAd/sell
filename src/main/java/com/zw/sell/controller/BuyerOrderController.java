package com.zw.sell.controller;

import com.zw.sell.ViewObject.ResultVO;
import com.zw.sell.converter.OrderFormToOrderDTO;
import com.zw.sell.dto.OrderDTO;
import com.zw.sell.entity.OrderDetail;
import com.zw.sell.enums.ResultEnum;
import com.zw.sell.exception.SellException;
import com.zw.sell.form.OrderForm;
import com.zw.sell.service.BuyerService;
import com.zw.sell.service.OrderService;
import com.zw.sell.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    //create order
    @PostMapping ("/create")
    public ResultVO<Map<String, String>> create (
            @Valid OrderForm orderForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("[CREATE ORDER] incorrect input, orderForm = {}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = OrderFormToOrderDTO.converter(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("[CREATE ORDER] order detail list is empty, orderDTO = {}", orderDTO.getOrderId());
            throw new SellException(ResultEnum.SHOPPING_CART_EMPTY_ERROR);
        }
        OrderDTO createResult = orderService.create(orderDTO);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());

        return ResultVOUtil.success(map);
    }

    //get order list
    @GetMapping("/list")
    public ResultVO<List<OrderDetail>> getOrderList(
            @RequestParam("openid") String openid,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size",defaultValue = "10") Integer size) {
        if (openid == null) {
            log.error("[CHECK ORDER DETAIL] openid can't be null");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid, pageRequest);

        return ResultVOUtil.success(orderDTOPage.getContent());

    }

    //get order detail list
    @GetMapping("/detail")
    public ResultVO getOrderDetail(@RequestParam String openid,
                                   @RequestParam String orderId){
        //TODO
        //findOne need to verify the openid with orderid

        OrderDTO orderDTO = buyerService.findOneOrderByOpenid(openid,orderId);
        return ResultVOUtil.success(orderDTO);

    }

    //cancel order
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam String openid,
                           @RequestParam String orderId){
        //TODO need use openid
        buyerService.cancelOrderByOpenid(openid,orderId);
        return ResultVOUtil.success();
    }
}
