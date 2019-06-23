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

    /**
     * driverName アプリケーションで使用するRDBのドライバークラス
     * url 接続先のデータベースのurl
     * username データベースで使用するユーザ名
     * password データベースで使用するパスワード
     * maxTotal データベース接続しているコネクションの最大値
     * maxIdle コネクションプールに待機しているコネクションの最大値
     * minIdle コネクションプールに待機しているコネクションの最小値
     * maxWaitMillis コネクションが使用され返却されるまでの時間の最大値
     */
    @Value("${database.driver-class-name}") private String driverName;
    @Value("${database.url}") private String url;
    @Value("${database.username}") private String username;
    @Value("${database.password}") private String password;
    @Value("${cp.maxTotal}") private int maxTotal;
    @Value("${cp.maxIdle}") private int maxIdle;
    @Value("${cp.minIdle}") private int minIdle;
    @Value("${cp.maxWaitMillis}") private long maxWaitMillis;

    /**
     * データソース設定
     * @return 上記設定でカスタマイズされたデータソース
     */
    @Bean(destroyMethod = "close") // アプリケーションが終了するタイミングでデータソースが解放されるように設定
    public DataSource dataSource() {
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
        return new DataSourceTransactionManager(dataSource());
    }

    /**
     * SqlSessionFactoryBeanのBean定義
     * @return アプリケーション起動時にSqlSessionFactoryを生成するSqlSessionFactoryBean
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource()
        );
        // MyBatis設定ファイルを指定
        sessionFactoryBean.setConfigLocation(
                new ClassPathResource("/mybatis-config.xml")
        );
        return sessionFactoryBean;
    }

}