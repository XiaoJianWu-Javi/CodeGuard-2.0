package es.tfg.codeguard.configuration.imp;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "es.tfg.codeguard.model.repository.exercisereport" }, 
                        entityManagerFactoryRef = "entityManagerFactoryExerciseReport", 
                        transactionManagerRef = "transactionManagerExerciseReport")
public class ExerciseReportDataSourceConfig {

    @Bean(name = "exerciseReportDataSource")
    @ConfigurationProperties(prefix = "spring.exercisereport.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:h2:mem:exerciseReport")
                .username("exerciseReport")
                .password("")
                .driverClassName("org.h2.Driver")
                .build();
    }

    @Bean(name = "entityManagerFactoryExerciseReport")
    public LocalContainerEntityManagerFactoryBean entityManager(
            EntityManagerFactoryBuilder builder,
            @Qualifier("exerciseReportDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("es.tfg.codeguard.model.entity.exercisereport")
                .persistenceUnit("exerciseReport")
                .build();
    }

    @Bean(name = "transactionManagerExerciseReport")
    public PlatformTransactionManager platformTransactionManager(
            @Qualifier("entityManagerFactoryExerciseReport") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
