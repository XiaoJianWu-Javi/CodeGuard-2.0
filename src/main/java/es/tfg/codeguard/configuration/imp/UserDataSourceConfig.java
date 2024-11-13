package es.tfg.codeguard.configuration.imp;

import es.tfg.codeguard.configuration.DataSourceConfig;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"es.tfg.codeguard.model.repository.user"},
        entityManagerFactoryRef = "entityManagerFactoryUsers",
        transactionManagerRef = "transactionManagerUser"
)
public class UserDataSourceConfig implements DataSourceConfig {

    @Primary
    @Bean(name = "usersDataSource")
    @ConfigurationProperties(prefix = "spring.users.datasource") //Rename this prefix in .properties to something better
    public DataSource dataSource(){
        return DataSourceBuilder.create()
                .url("jdbc:h2:mem:users")
                .username("users")
                .password("")
                .driverClassName("org.h2.Driver")
                .build();
    }

    @Primary
    @Bean(name = "entityManagerFactoryUsers")
    public LocalContainerEntityManagerFactoryBean entityManager(
            EntityManagerFactoryBuilder builder,
            @Qualifier("usersDataSource")DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("es.tfg.codeguard.model.entity.user")
                .persistenceUnit("users")
                .build();
    }

    @Primary
    @Bean(name = "transactionManagerUser")
    public PlatformTransactionManager platformTransactionManager(
            @Qualifier("entityManagerFactoryUsers") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}