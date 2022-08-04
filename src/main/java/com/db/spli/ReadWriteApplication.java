package com.db.spli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author lvxuan
 * @description 启动类
 */
@SpringBootApplication
@EnableCaching
public class ReadWriteApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReadWriteApplication.class, args);
    }
}
