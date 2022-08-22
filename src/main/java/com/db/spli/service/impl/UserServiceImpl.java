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
    public void getCache() {
        Collection<String> cacheNames = myCacheManagerV2.getCacheNames();
        for (String cacheName : cacheNames) {
            CaffeineCache cache = (CaffeineCache) myCacheManagerV2.getCache(cacheName);
            Cache<Object, Object> nativeCache1 = cache.getNativeCache();
            int size = nativeCache1.asMap().size();
            Object nativeCache = cache.getNativeCache();
        }
    }
}
