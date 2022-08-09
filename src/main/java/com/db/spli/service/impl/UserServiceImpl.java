package com.db.spli.service.impl;

import com.db.spli.constant.CacheConstant;
import com.db.spli.domain.User;
import com.db.spli.mapper.UserMapper;
import com.db.spli.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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

    @Override
//    @CachePut(cacheNames = CacheConstant.TEN_MINUTES, key = "#user.id")
    public User insertOne(User user) {
        userMapper.insertSelective(user);
        return user;
    }

    @Override
//    @Cacheable(cacheNames = CacheConstant.ONE_HOUR, key = "#id")
    public User queryOneById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
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
}
