package com.db.spli.service.impl;

import com.db.spli.constant.CacheConstant;
import com.db.spli.domain.User;
import com.db.spli.mapper.UserMapper;
import com.db.spli.service.UserService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lvxuan
 * @description user接口实现类
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    @CachePut(cacheNames = CacheConstant.TEN_MINUTES, key = "#user.id")
    public User insertOne(User user) {
        userMapper.insertSelective(user);
        return user;
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.ONE_HOUR, key = "#id")
    public User queryOneById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
