package com.zw.sell.constant;

/**
 * redis constant
 * expire time: 2 hours
 */
public interface RedisConstant {

    String TOKEN_PEFIX = "token_%s";

    Integer EXPIRE = 2 * 60 * 60; // 2 hours
}
