package org.aidan.ssm.rwdb;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Aidan
 * @创建时间：2019/6/13 11:45 PM
 * @描述: TODO
 */
@Configuration
public class DynamicDataSourceConfig {

    @Bean
    public DruidDataSource dataSourceWrite() {
        DruidDataSource druidDataSource = new DruidDataSource();
        setDataSourceProperties(druidDataSource);
        druidDataSource.setUrl("jdbc:mysql://127.0.0.1:3307/ssm?serverTimezone=UTC&characterEncoding=utf-8&autoReconnect=true&allowMultiQueries=true");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("P2ssw0rd");
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        return druidDataSource;
    }

    @Bean
    public DruidDataSource dataSourceRead() {
        DruidDataSource druidDataSource = new DruidDataSource();
        setDataSourceProperties(druidDataSource);
        druidDataSource.setUrl("jdbc:mysql://127.0.0.1:3308/ssm?serverTimezone=UTC&characterEncoding=utf-8&autoReconnect=true&allowMultiQueries=true");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("P2ssw0rd");
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        return druidDataSource;
    }

    private void setDataSourceProperties(DruidDataSource druidDataSource) {
        druidDataSource.setInitialSize(1);
        druidDataSource.setMinIdle(10);
        druidDataSource.setMaxActive(10);
        druidDataSource.setMaxWait(60000);
    }

    @Bean
    public DynamicDataSource dataSource() {
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setWriteDataSource(dataSourceWrite());
        dataSource.setReadDataSource(dataSourceRead());
        return dataSource;
    }

    // TODO

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource());
        return sqlSessionFactory;
    }

    @Bean
    public DynamicDataSourceTransactionManager transactionManager() {
        DynamicDataSourceTransactionManager dynamicDataSourceTransactionManager = new DynamicDataSourceTransactionManager();
        dynamicDataSourceTransactionManager.setDataSource(dataSource());
        return dynamicDataSourceTransactionManager;
    }

}
