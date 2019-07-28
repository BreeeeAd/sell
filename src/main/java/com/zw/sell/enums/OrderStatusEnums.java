package com.zw.sell.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnums implements CodeEnums{

    NEW (0, "new order"),
    FINISHED(1,"finished order"),
    CANCEL(2,"canceled order")
    ;

    private Integer code;

    private String msg;

    OrderStatusEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
