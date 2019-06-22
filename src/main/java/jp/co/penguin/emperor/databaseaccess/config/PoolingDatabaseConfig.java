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
@MapperScan("jp.co.penguin.emperor.databaseaccess.mapper") // 使用するMapperインターフェースのパッケージを指定
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

    /**
     * データソース設定
     * @param driverName アプリケーションで使用するRDBのドライバークラス
     * @param url 接続先のデータベースのurl
     * @param username データベースで使用するユーザ名
     * @param password データベースで使用するパスワード
     * @param maxTotal データベース接続しているコネクションの最大値
     * @param maxIdle コネクションプールに待機しているコネクションの最大値
     * @param minIdle コネクションプールに待機しているコネクションの最小値
     * @param maxWaitMillis コネクションが使用され返却されるまでの時間の最大値
     * @return 上記設定でカスタマイズされたデータソース
     */
    @Bean(destroyMethod = "close") // アプリケーションが終了するタイミングでデータソースが解放されるように設定
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

    /**
     * TransactionManagerのBean定義
     * @return TransactionManager
     */
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

    /**
     * SqlSessionFactoryBeanのBean定義
     * @return アプリケーション起動時にSqlSessionFactoryを生成するSqlSessionFactoryBean
     */
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
        // MyBatis設定ファイルを指定
        sessionFactoryBean.setConfigLocation(
                new ClassPathResource("/mybatis-config.xml")
        );
        return sessionFactoryBean;
    }

}