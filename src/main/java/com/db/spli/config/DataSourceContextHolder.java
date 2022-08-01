package com.db.spli.config;

/**
 * @author lvxuan
 * @description 保存线程数据源信息
 */
public class DataSourceContextHolder {
    private static final ThreadLocal<String> DATA_SOURCE = new ThreadLocal<String>();
    static {
        DATA_SOURCE.set("hello");
    }

    public static void set(String dataSource) {
        DATA_SOURCE.set(dataSource);
    }

    public static String get() {
        return DATA_SOURCE.get();
    }

    public static void clear() {
        DATA_SOURCE.remove();
    }
}
