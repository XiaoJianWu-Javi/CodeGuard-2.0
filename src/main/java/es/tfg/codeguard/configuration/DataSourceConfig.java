package es.tfg.codeguard.configuration;

import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

public interface DataSourceConfig {

    public DataSource dataSource();

    public LocalContainerEntityManagerFactoryBean entityManager(
            EntityManagerFactoryBuilder builder,
            DataSource dataSource
    );

}
