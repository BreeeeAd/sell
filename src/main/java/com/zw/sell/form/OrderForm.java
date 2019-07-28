package com.zw.sell.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class OrderForm {

    @NotEmpty(message = "Name required")
    private String name;
    @NotEmpty(message = "Phone number required")
    private String phone;
    @NotEmpty(message = "Address required")
    private String address;
    @NotEmpty(message = "Buyer's Openid required")
    private String openid;
    @NotEmpty(message = "Items can't be empty")
    private String items;


}
