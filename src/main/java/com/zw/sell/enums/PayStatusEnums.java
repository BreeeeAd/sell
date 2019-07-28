package com.zw.sell.enums;

import lombok.Getter;

@Getter
public enum PayStatusEnums implements CodeEnums {
    WAIT(0, "waiting pay"),
    SUCCESS(1,"already paid");

    private Integer code;

    private String msg;

    PayStatusEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
