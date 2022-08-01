package com.db.spli.mapper;

import com.db.spli.aop.Dss;
import com.db.spli.domain.User;
import com.db.spli.enums.DataSourceEnum;

/**
 * @author lvxuan
 * @description user mapper
 */
public interface UserMapper {
    int deleteByPrimaryKey(Long userId);

    @Dss(value = DataSourceEnum.MASTER)
    int insert(User record);

    @Dss(value = DataSourceEnum.MASTER)
    int insertSelective(User record);

//    @Dss(value = DataSourceEnum.SLAVE)
    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
