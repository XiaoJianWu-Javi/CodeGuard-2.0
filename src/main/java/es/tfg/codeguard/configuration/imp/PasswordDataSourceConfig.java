package es.tfg.codeguard.configuration.imp;

import es.tfg.codeguard.configuration.DataSourceConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "es.tfg.codeguard",
        entityManagerFactoryRef = "passwordEntityManagerFactory"
)
public class PasswordDataSourceConfig implements DataSourceConfig {

    @Bean(name = "passwordDataSource")
    @ConfigurationProperties(prefix = "spring.password.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder.create()
                .url("jdbc:h2:mem:password")
                .username("password")
                .password("")
                .driverClassName("org.h2.Driver")
                .build();
    }

    @Bean(name = "passwordEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManager(
            EntityManagerFactoryBuilder builder,
            @Qualifier("passwordDataSource")DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("es.tfg.codeguard")
                .persistenceUnit("password")
                .build();
    }
}
