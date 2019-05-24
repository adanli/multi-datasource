package com.multi.common.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.multi.common.constant.DatabaseTypeEnum;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author adanl
 */
@Configuration
public class MyBatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor interceptor = new PaginationInterceptor();
        // 设置方言类型
        interceptor.setDialectType("mysql");

        return interceptor;
    }

    /**
     * 定义user
     * @return
     */
    @Bean(name = "user")
    @ConfigurationProperties(prefix = "jdbc.datasource")
    public DataSource user() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 定义project
     * @return
     */
    @Bean(name = "project")
    @ConfigurationProperties(prefix = "jdbc2.datasource")
    public DataSource project() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DataSource multipleDataSource(@Qualifier("user") DataSource db1, @Qualifier("project") DataSource db2) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DatabaseTypeEnum.user.name(), db1);
        targetDataSources.put(DatabaseTypeEnum.project.name(), db2);
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(db1);
        return dynamicDataSource;
    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        //***导入MybatisSqlSession配置***
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        //指明数据源
        sqlSessionFactory.setDataSource(multipleDataSource(user(), project()));
        //指明mapper.xml位置(配置文件中指明的xml位置会失效用此方式代替，具体原因未知)
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/**Mapper.xml"));
        //指明实体扫描(多个package用逗号或者分号分隔)
        sqlSessionFactory.setTypeAliasesPackage("com.multi.common.db.mapper");

        //***导入Mybatis配置***
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        sqlSessionFactory.setConfiguration(configuration);
        sqlSessionFactory.setPlugins(new Interceptor[]{paginationInterceptor()});

        //***导入全局配置***
//        sqlSessionFactory.setGlobalConfig(globalConfiguration());
        return sqlSessionFactory.getObject();
    }
/*
    @Bean
    public GlobalConfiguration globalConfiguration() {
        GlobalConfiguration conf = new GlobalConfiguration(new LogicSqlInjector());
        //主键类型 0:数据库ID自增, 1:用户输入ID,2:全局唯一ID (数字类型唯一ID), 3:全局唯一ID UUID
        conf.setIdType(0);
        //字段策略(拼接sql时用于判断属性值是否拼接) 0:忽略判断,1:非NULL判断,2:非空判断
        conf.setFieldStrategy(2);
        //驼峰下划线转换含查询column及返回column(column下划线命名create_time，返回java实体是驼峰命名createTime，开启后自动转换否则保留原样)
        conf.setDbColumnUnderline(true);
        //是否动态刷新mapper
        conf.setRefresh(true);
        return conf;
    }*/



}
