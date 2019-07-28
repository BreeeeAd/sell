package com.zw.sell.utils;

import java.util.Random;

public class KeyUtil {

    // generation unique ID
    // syntax: time + rand
    public static synchronized String genUniqueKey(){
        Random random = new Random();

        Integer rand = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(rand);
    }
}
