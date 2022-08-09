package com.db.spli.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.db.spli.enums.DataSourceEnum;
import com.db.spli.enums.SlaveDataEnum;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: lvxuan
 * @program:
 * @Date: 2022/7/31 23:13
 * @Version: 1.0
 * @motto: 而后乃将图南
 * @Description: des
 * ░░░░░░░░░░░░░░░░░░░░░░░░▄░░
 * ░░░░░░░░░▐█░░░░░░░░░░░▄▀▒▌░
 * ░░░░░░░░▐▀▒█░░░░░░░░▄▀▒▒▒▐
 * ░░░░░░░▐▄▀▒▒▀▀▀▀▄▄▄▀▒▒▒▒▒▐
 * ░░░░░▄▄▀▒░▒▒▒▒▒▒▒▒▒█▒▒▄█▒▐
 * ░░░▄▀▒▒▒░░░▒▒▒░░░▒▒▒▀██▀▒▌
 * ░░▐▒▒▒▄▄▒▒▒▒░░░▒▒▒▒▒▒▒▀▄▒▒
 * ░░▌░░▌█▀▒▒▒▒▒▄▀█▄▒▒▒▒▒▒▒█▒▐
 * ░▐░░░▒▒▒▒▒▒▒▒▌██▀▒▒░░░▒▒▒▀▄
 * ░▌░▒▄██▄▒▒▒▒▒▒▒▒▒░░░░░░▒▒▒▒
 * ▀▒▀▐▄█▄█▌▄░▀▒▒░░░░░░░░░░▒▒▒
 * You are not expected to understand this
 */
@Configuration
@MapperScan(basePackages = "com.db.spli.mapper", sqlSessionTemplateRef = "mySqlSessionTemplate")
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDb() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.slave1")
    public DataSource slaveDbOne() {
        return DruidDataSourceBuilder.create().build();
    }

//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource.slave2")
//    public DataSource slaveDbTwo() {
//        return DruidDataSourceBuilder.create().build();
//    }

    @Bean
    public DynamicDataSource dynamicDb(@Qualifier("masterDb") DataSource masterDataSource,
                                       @Autowired(required = false) @Qualifier("slaveDbOne") DataSource slaveDataSourceOne
//            ,
//                                       @Autowired(required = false) @Qualifier("slaveDbTwo") DataSource slaveDataSourceTwo
    ) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSourceMap = new HashMap<Object, Object>();
        targetDataSourceMap.put(DataSourceEnum.MASTER.getDataSourceName(), masterDataSource);
//        targetDataSourceMap.put(SlaveDataEnum.SLAVE_ONE.getMessage())
        targetDataSourceMap.put(DataSourceEnum.SLAVE.getDataSourceName(), slaveDataSourceOne);
        dynamicDataSource.setTargetDataSources(targetDataSourceMap);
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
        return dynamicDataSource;
    }

    @Bean
    public SqlSessionFactory mySqlSessionFactory(@Qualifier("dynamicDb") DataSource dynamicDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver()
                        .getResources("classpath*:mapper/*Mapper.xml")
        );
        sqlSessionFactoryBean.setDataSource(dynamicDataSource);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate mySqlSessionTemplate(@Qualifier("mySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public DataSourceTransactionManager myDataSourceTM(@Qualifier("dynamicDb") DataSource dynamicDataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dynamicDataSource);
        return dataSourceTransactionManager;
    }
}
