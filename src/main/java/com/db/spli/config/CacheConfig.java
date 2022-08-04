package com.db.spli.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author lvxuan
 * @description 缓存配置类
 */
@Configuration
public class CacheConfig {

    @Bean(name = "myCacheManager")
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterAccess(600, TimeUnit.SECONDS)
                .initialCapacity(1)
                .maximumSize(1)
        );
        return cacheManager;
    }


}
