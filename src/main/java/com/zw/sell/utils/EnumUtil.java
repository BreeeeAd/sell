package com.zw.sell.utils;

import com.zw.sell.enums.CodeEnums;

public class EnumUtil {

    public static <T extends CodeEnums> T getByCode(Integer code, Class<T> enumClass){
        for (T each : enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())){
                return each;
            }
        }
        return null;
    }
}
