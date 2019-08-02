package com.zw.sell.handler;


import com.zw.sell.exception.SellAuthorizeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class SellerExceptionHandler {


    // handle the login exception
    @ExceptionHandler(value = SellAuthorizeException.class)
    public ModelAndView handlerAuthorizeException(){

        return new ModelAndView("redirect:" + "/seller/login");

    }
}
