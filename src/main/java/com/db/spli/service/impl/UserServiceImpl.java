package com.db.spli.service.impl;

import com.db.spli.domain.User;
import com.db.spli.mapper.UserMapper;
import com.db.spli.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void insertOne(User user) {
        userMapper.insertSelective(user);
    }

    @Override
    public User queryOneById(Integer id) {

        return userMapper.selectByPrimaryKey(id);
    }
}
