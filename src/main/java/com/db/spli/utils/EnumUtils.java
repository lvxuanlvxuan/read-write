package com.db.spli.utils;

import com.db.spli.enums.BaseEnum;

/**
 * @author lvxuan
 * @description 枚举工具类
 */
public class EnumUtils {

    public static String getMessageByCode(BaseEnum[] baseEnums, Integer code) {
        for (BaseEnum baseEnum : baseEnums) {
            if (baseEnum.getCode().equals(code)) {
                return baseEnum.getMessage();
            }
        }
        return null;
    }

}
