package com.db.spli.service.impl;

import com.db.spli.constant.CacheConstant;
import com.db.spli.domain.User;
import com.db.spli.mapper.UserMapper;
import com.db.spli.service.UserService;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * @author lvxuan
 * @description user接口实现类
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Autowired
    private CacheManager myCacheManagerV2;

    private static CaffeineCache ONE_HOUR;

    @PostConstruct
    private void initCaffeine() {
        ONE_HOUR = (CaffeineCache) myCacheManagerV2.getCache(CacheConstant.ONE_HOUR);
    }


    @Override
//    @CachePut(cacheNames = CacheConstant.TEN_MINUTES, key = "#user.id")
    public User insertOne(User user) {
        userMapper.insertSelective(user);
        return user;
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.ONE_HOUR, key = "#id")
    public User queryOneById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> queryByIdList(List<Integer> list) {
        List<User> users = userMapper.queryByIdList(list);
        ONE_HOUR.put("test", users);
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public void updateById(String address, Integer id) {
        userMapper.updateAddressById(address, id);
        System.out.println(1 / 0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public void testTransaction(User user) {
        userMapper.insertSelective(user);
        System.out.println(1 / 0);
//        updateById("taiwanm", 10);
    }

    @Override
    public void getCacheAll() {
        Collection<String> cacheNames = myCacheManagerV2.getCacheNames();
        for (String cacheName : cacheNames) {
            CaffeineCache cache = (CaffeineCache) myCacheManagerV2.getCache(cacheName);
            Cache<Object, Object> nativeCache1 = cache.getNativeCache();
            int size = nativeCache1.asMap().size();
            Object nativeCache = cache.getNativeCache();
        }
    }

    @Override
    public User getCache(Integer key) {
        User o = (User) ONE_HOUR.getNativeCache().get(key, new Function<Object, Object>() {
            @Override
            public Object apply(Object o) {
                User user = new User();
                user.setId(1);
                user.setUserName("xiaolv");
                return user;
            }
        });
        return o;
    }

    @Override
    public void putCache() {
        for (int i = 0; i < 30; i++) {
            User user1=new User();
            user1.setId(i);
            user1.setUserName("xiaofeng"+1);
            ONE_HOUR.put(i, user1);
        }
    }

    @Override
    public void evictCache(Integer key) {
        ONE_HOUR.evict(key);
//        ONE_HOUR.getNativeCache().invalidate(key);
    }

    @Override
    public void invalidCache() {
        ONE_HOUR.invalidate();
    }
}
