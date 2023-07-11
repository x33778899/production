//package com.example.demo.config;
//
//import javax.sql.DataSource;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.mapper.MapperScannerConfigurer;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//@Configuration
//public class DataSourceConfig {
//
//    @Bean(name = "db1DataSource")
//    @ConfigurationProperties(prefix = "datasource.db1")
//    public DataSource db1DataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "db2DataSource")
//    @ConfigurationProperties(prefix = "datasource.db2")
//    public DataSource db2DataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "db1JdbcTemplate")
//    public JdbcTemplate db1JdbcTemplate(@Qualifier("db1DataSource") DataSource db1DataSource) {
//        return new JdbcTemplate(db1DataSource);
//    }
//
//    @Bean(name = "db2JdbcTemplate")
//    public JdbcTemplate db2JdbcTemplate(@Qualifier("db2DataSource") DataSource db2DataSource) {
//        return new JdbcTemplate(db2DataSource);
//    }
//
//    @Bean
//    public SqlSessionFactory sqlSessionFactory(@Qualifier("db1DataSource") DataSource db1DataSource) throws Exception {
//        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
//        sessionFactory.setDataSource(db1DataSource);
//        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
//        return sessionFactory.getObject();
//    }
//}
