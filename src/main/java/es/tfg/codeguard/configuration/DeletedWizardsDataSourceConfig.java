package es.tfg.codeguard.configuration;

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
        entityManagerFactoryRef = "deadWizardsEntityManagerFactory"
)
public class DeletedWizardsDataSourceConfig {

    @Bean(name = "deadWizardsDataSource")
    @ConfigurationProperties(prefix = "spring.deleted.datasource")
    public DataSource deadWizardsDataSource(){
        return DataSourceBuilder.create()
                .url("jdbc:h2:mem:deletedWizards")
                .username("deleted")
                .password("")
                .driverClassName("org.h2.Driver")
                .build();
    }

    @Bean(name = "deadWizardsEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean deadWizardsEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("deadWizardsDataSource")DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("es.tfg.codeguard")
                .persistenceUnit("deadWizards")
                .build();
    }
}
