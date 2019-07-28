package com.zw.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    SUCCESS(0,"success"),
    PARAM_ERROR(1,"input param error"),
    PRODUCT_NOT_EXIST(10,"product donnot exist"),
    PRODUCT_STOCK_ERROR(11,"product stack error"),
    ORDER_NOT_EXIST(12,"order not exist"),
    ORDER_DETAILS_NOT_EXIST(13,"order details not exist"),
    ORDER_STATUS_ERROR(14,"order status incorrect"),
    ORDER_UPDATED_FAIL(15,"failed to update order status"),
    ORDER_DETAIL_EMPTY(16,"order detail list is empty"),
    ORDER_PAY_STATUS_ERROR(17,"order pay status error"),
    SHOPPING_CART_EMPTY_ERROR(18,"shopping cart can't be empty"),
    ORDER_OWNER_ERROR(19,"openid isn't same as current order's openid"),
    ORDER_CANCEL_SUCCESS(22, "success cancel order"),
    ORDER_FINISH_SUCCESS(23,"success finish order"),
    PRODUCT_STATUS_ERROR(24,"product status error"),
    PRODUCT_STATUS_CHANGE_SUCCESS(25,"success change product status");

    private Integer code;

    private String msg;


    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
