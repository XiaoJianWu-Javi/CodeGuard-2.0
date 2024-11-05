package es.tfg.codeguard.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public interface DataSourceConfig {

    @Bean(name = "wizardsDB")
    @ConfigurationProperties(prefix = "spring.datasource1")
    public DataSource dataSource1();

    @Bean(name = "deadWizardsDB")
    @ConfigurationProperties(prefix = "spring.datasource2")
    public DataSource dataSource2();

    @Bean(name = "passwordDB")
    @ConfigurationProperties(prefix = "spring.datasource3")
    public DataSource dataSource3();

}
