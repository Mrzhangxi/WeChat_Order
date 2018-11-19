package com.zx.sell.utils;

import com.zx.sell.enums.CodeEnum;

public class EnumUtil {
    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each: enumClass.getEnumConstants()) {
//            System.out.println(each);
//            此处each是Enum得枚举量
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
