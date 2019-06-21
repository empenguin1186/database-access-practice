package jp.co.penguin.emperor.databaseaccess.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan("jp.co.penguin.emperor.databaseaccess")
@PropertySource("classpath:application.yml")
@MapperScan("jp.co.penguin.emperor.databaseaccess.mapper")
@EnableTransactionManagement
public class PoolingDatabaseConfig {

    @Value("${database.driver-class-name}") String driverName;
    @Value("${database.url}") String url;
    @Value("${database.username}") String username;
    @Value("${database.password}") String password;
    @Value("${cp.maxTotal}") int maxTotal;
    @Value("${cp.maxIdle}") int maxIdle;
    @Value("${cp.minIdle}") int minIdle;
    @Value("${cp.maxWaitMillis}") long maxWaitMillis;


    @Bean(destroyMethod = "close")
    public DataSource dataSource(
            @Value("${database.driver-class-name}") String driverName,
            @Value("${database.url}") String url,
            @Value("${database.username}") String username,
            @Value("${database.password}") String password,
            @Value("${cp.maxTotal}") int maxTotal,
            @Value("${cp.maxIdle}") int maxIdle,
            @Value("${cp.minIdle}") int minIdle,
            @Value("${cp.maxWaitMillis}") long maxWaitMillis) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaxTotal(maxTotal);
        dataSource.setMaxIdle(maxIdle);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxWaitMillis(maxWaitMillis);
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource(
                driverName,
                url,
                username,
                password,
                maxTotal,
                maxIdle,
                minIdle,
                maxWaitMillis
        ));
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource(
                driverName,
                url,
                username,
                password,
                maxTotal,
                maxIdle,
                minIdle,
                maxWaitMillis
                )
        );
        sessionFactoryBean.setConfigLocation(
                new ClassPathResource("/mybatis-config.xml")
        );
        return sessionFactoryBean;
    }

}