package com.db.spli.service;

import com.db.spli.domain.User;

/**
 * @author lvxuan
 * @description user服务接口
 */
public interface UserService {

    User insertOne(User user);

    User queryOneById(Integer id);
}
