package org.superbiz.moviefun;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DbConfig {

    @Bean
    @ConfigurationProperties("moviefun.datasources.albums")
    public DataSource albumsDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("moviefun.datasources.movies")
    public DataSource moviesDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    HibernateJpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter movieJpaVendorAdapter = new HibernateJpaVendorAdapter();
        movieJpaVendorAdapter.setDatabase(Database.MYSQL);
        movieJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
        movieJpaVendorAdapter.setGenerateDdl(true);


        return movieJpaVendorAdapter;
    }




    @Bean
    LocalContainerEntityManagerFactoryBean albumsEntityManagerFactory(@Qualifier("albumsDataSource") DataSource albumsDataSource,HibernateJpaVendorAdapter jpaVendorAdapter) {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create");
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(albumsDataSource);
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        factoryBean.setPackagesToScan("org.superbiz.moviefun.albums");
        factoryBean.setPersistenceUnitName("albums");
        factoryBean.setJpaProperties(properties);
        return factoryBean;
    }



    @Bean
    LocalContainerEntityManagerFactoryBean moviesEntityManagerFactory(@Qualifier("moviesDataSource") DataSource moviesDataSource, HibernateJpaVendorAdapter jpaVendorAdapter) {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create");
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(moviesDataSource);
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        factoryBean.setPackagesToScan("org.superbiz.moviefun.movies");
        factoryBean.setPersistenceUnitName("movies");
        factoryBean.setJpaProperties(properties);
        return factoryBean;
    }

    @Bean
    PlatformTransactionManager moviePlatformTransactionManager ( @Qualifier("moviesEntityManagerFactory") LocalContainerEntityManagerFactoryBean moviesEntityManagerFactory){

        return  new JpaTransactionManager(moviesEntityManagerFactory.getObject());
    }


    @Bean
    PlatformTransactionManager albumPlatformTransactionManager ( @Qualifier("albumsEntityManagerFactory") LocalContainerEntityManagerFactoryBean albumsEntityManagerFactory){

        return  new JpaTransactionManager(albumsEntityManagerFactory.getObject());
    }





}
