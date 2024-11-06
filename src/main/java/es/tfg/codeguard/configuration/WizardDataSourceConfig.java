package es.tfg.codeguard.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "es.tfg.codeguard",
        entityManagerFactoryRef = "wizardsEntityManagerFactory"
)
public class WizardDataSourceConfig {

    @Primary
    @Bean(name = "wizardsDataSource")
    @ConfigurationProperties(prefix = "spring.wizards.datasource") //Rename this prefix in .properties to something better
    public DataSource wizardsDataSource(){
        return DataSourceBuilder.create()
                .url("jdbc:h2:mem:wizards")
                .username("wizards")
                .password("")
                .driverClassName("org.h2.Driver")
                .build();
    }

    @Primary
    @Bean(name = "wizardsEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean wizardsEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("wizardsDataSource")DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("es.tfg.codeguard")
                .persistenceUnit("wizards")
                .build();
    }

}
