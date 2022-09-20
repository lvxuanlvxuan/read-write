package com.db.spli.service.impl;

import com.db.spli.domain.User;
import com.db.spli.mapper.UserMapper;
import com.db.spli.service.UserServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceV2Impl implements UserServiceV2 {

    @Resource
    private UserMapper userMapper;

    @Override
    public void testClassTransaction() {
        User user = userMapper.selectByPrimaryKey(7);
        user.setId(null);
        user.setUserName("xiaowu");
        userMapper.insertSelective(user);
    }
}
