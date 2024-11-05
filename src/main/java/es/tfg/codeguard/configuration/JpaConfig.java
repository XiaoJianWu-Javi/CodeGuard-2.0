package es.tfg.codeguard.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public interface JpaConfig {

    @Primary
    @Bean(name = "wizardsEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean wizardsEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("wizardsDB") DataSource dataSource);

    @Primary
    @Bean(name = "deadWizardsEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean deadWizardsEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("deadWizardsDB") DataSource dataSource);

    @Primary
    @Bean(name = "passwordEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean passwordEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("passwordDB") DataSource dataSource);


}
