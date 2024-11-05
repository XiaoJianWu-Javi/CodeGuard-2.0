package es.tfg.codeguard.configuration.imp;

import es.tfg.codeguard.configuration.JpaConfig;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

public class JpaConfigImp implements JpaConfig {

    @Override
    public LocalContainerEntityManagerFactoryBean wizardsEntityManagerFactory(EntityManagerFactoryBuilder builder, DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("es.tfg.codeguard.model.entity")
                .persistenceUnit("codeguard-wizards-db")
                .build();
    }

    @Override
    public LocalContainerEntityManagerFactoryBean deadWizardsEntityManagerFactory(EntityManagerFactoryBuilder builder, DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("es.tfg.codeguard.model.entity")
                .persistenceUnit("codeguard-deleted-wizards-db")
                .build();
    }

    @Override
    public LocalContainerEntityManagerFactoryBean passwordEntityManagerFactory(EntityManagerFactoryBuilder builder, DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("es.tfg.codeguard.model.entity")
                .persistenceUnit("codeguard-password-db")
                .build();
    }
}
