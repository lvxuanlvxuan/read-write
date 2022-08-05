package com.db.spli.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author lvxuan
 * @description 从库枚举
 */
@Getter
@AllArgsConstructor
public enum SlaveDataEnum {

    SLAVE_ONE(1, "slaveOne"),
    SLAVE_TWO(2, "slaveTwo");

    private Integer code;
    private String message;
}
