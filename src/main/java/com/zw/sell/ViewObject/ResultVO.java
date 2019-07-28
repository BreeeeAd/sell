package com.zw.sell.ViewObject;


import lombok.Data;

@Data
public class ResultVO <T>{
    private Integer code;

    private String message;

    private T data;
}
