package com.db.spli.config;

import com.db.spli.constant.CacheConstant;
import com.db.spli.domain.User;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author lvxuan
 * @description 缓存配置类
 */
@Configuration
public class CacheConfig {


//    @Bean(name = "myCacheManager")
//    public CacheManager myCacheManager() {
//        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
//        cacheManager.setCaffeine(Caffeine.newBuilder()
//                .expireAfterAccess(600, TimeUnit.SECONDS)
//                .initialCapacity(1)
//                .maximumSize(1)
//        );
//        return cacheManager;
//    }

    @Bean(name = "myCacheManagerV2")
    public CacheManager myCacheManagerV2() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<CaffeineCache> caches = new ArrayList<>();
        caches.add(new CaffeineCache(CacheConstant.TEN_MINUTES,
                Caffeine.newBuilder().expireAfterAccess(600, TimeUnit.SECONDS)
                        .initialCapacity(50)
                        .maximumSize(300)
                        .build()));
        caches.add(new CaffeineCache(CacheConstant.ONE_HOUR,
                Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.HOURS)
                        .initialCapacity(1)
                        .maximumSize(20)
                        .build()));
        caches.add(new CaffeineCache(CacheConstant.THREE_HOUR,
                Caffeine.newBuilder().expireAfterWrite(3, TimeUnit.HOURS)
                        .initialCapacity(50)
                        .maximumSize(300)
                        .build(new CacheLoader<Object, Object>() {
                            @Override
                            public Object load(Object o) throws Exception {
                                return initUser();
                            }
                        })));
        cacheManager.setCaches(caches);
        return cacheManager;
    }

    /**
     * 可以改为数据库操作
     *
     * @return
     */
    private User initUser() {
        User user = new User();
        user.setId(1);
        user.setUserName("xiaolv");
        return user;
    }
}
