package com.db.spli.service;

import com.db.spli.domain.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author lvxuan
 * @description user服务接口
 */
public interface UserService {

    User insertOne(User user);

    User queryOneById(Integer id);

    void updateById(String address, Integer id);

    void testTransaction(User user);

}
