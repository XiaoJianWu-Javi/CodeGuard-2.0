package es.tfg.codeguard.configuration.imp;

import es.tfg.codeguard.configuration.DataSourceConfig;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

public class DataSourceConfigImp implements DataSourceConfig{

    public DataSource dataSource1() {
        return DataSourceBuilder.create().build();
    }

    public DataSource dataSource2() {
        return DataSourceBuilder.create().build();
    }

    public DataSource dataSource3() {
        return DataSourceBuilder.create().build();
    }

}
