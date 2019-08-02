package com.zw.sell.controller;

import com.zw.sell.dto.OrderDTO;
import com.zw.sell.enums.ResultEnum;
import com.zw.sell.exception.SellException;
import com.zw.sell.service.BuyerService;
import com.zw.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    /**
     * order list
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10") Integer size,
                             Map<String, Object> map){
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(pageRequest);
        map.put("orderDTOPage", orderDTOPage);
        map.put("currentPage", page);
        map.put("currentSize", size);
        return new ModelAndView("order/list", map);

    }

    /**
     * cancel order
     * @param orderId
     * @return
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId,
                               Map<String, Object> map){
        try{
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        } catch (SellException e){
            log.error("[SELLER CANCEL ORDER] error occur" );
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("/common/error",  map);
        }
        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMsg());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("/common/success");
    }

    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId,
                               Map<String, Object> map){
        OrderDTO orderDTO = new OrderDTO();
        try{
            orderDTO = orderService.findOne(orderId);
            map.put("orderDetailList", orderDTO.getOrderDetailList());
        } catch (SellException e){
            log.error("[CHECK ORDER] order detail not exists",e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("/common/error",map);
        }
        map.put("orderDTO", orderDTO);
        return new ModelAndView("order/detail",map);
    }

    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId") String orderId,
                               Map<String, Object> map){
        OrderDTO orderDTO = new OrderDTO();
        try{
            orderDTO = orderService.findOne(orderId);
            orderService.finish(orderDTO);
        } catch (SellException e){
            log.error("[FINISH ORDER] can't finish order or order status incorrect", e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("/common/error",map);
        }
        map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMsg());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("/common/success");
    }

}
