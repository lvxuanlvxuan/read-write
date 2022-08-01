package com.db.spli.aop;

import com.db.spli.enums.DataSourceEnum;

import java.lang.annotation.*;

/**
 * @author lvxuan
 * @dscription 指定数据源注解
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Dss {

    DataSourceEnum value() default DataSourceEnum.MASTER;

    boolean clear() default true;
}
