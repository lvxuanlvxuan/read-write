package com.db.spli.mapper;

import com.db.spli.aop.Dss;
import com.db.spli.domain.User;
import com.db.spli.enums.DataSourceEnum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    @Dss(value = DataSourceEnum.SLAVE)
    User selectByPrimaryKey(Integer userId);

    @Dss(value = DataSourceEnum.SLAVE)
    List<User> queryByIdList(@Param("list") List<Integer> list);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);


    void updateAddressById(@Param("address") String address,
                           @Param("id") Integer id);
}
