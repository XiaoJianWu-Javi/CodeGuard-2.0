package es.tfg.codeguard.configuration;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

public interface DataSourceConfig {

    public DataSource dataSource();

    public LocalContainerEntityManagerFactoryBean entityManager(
            EntityManagerFactoryBuilder builder,
            DataSource dataSource
    );

    public PlatformTransactionManager platformTransactionManager(
            EntityManagerFactory entityManagerFactory
    );

}