package com.db.spli.controller;

import com.db.spli.domain.User;
import com.db.spli.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * @author lvxuan
 * @description user Controller
 */
@RestController
@RequestMapping("/controller-user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CacheManager myCacheManagerV2;

    @PostMapping("/insertOne")
    public ResponseEntity insertOne(@RequestBody User user) {
        userService.insertOne(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/queryOneById")
    public ResponseEntity queryOneById(@RequestParam("id") Integer id) {
        User user = userService.queryOneById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getCache")
    public ResponseEntity getCache() {
        Collection<String> cacheNames = myCacheManagerV2.getCacheNames();
        for (String cacheName : cacheNames) {
            Cache cache = myCacheManagerV2.getCache(cacheName);
            Object nativeCache = cache.getNativeCache();
        }
        return ResponseEntity.ok().build();
    }
}
