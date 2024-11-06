package es.tfg.codeguard.dbtest;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DataSourceConfigTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void correctBeans(){

        DataSource wizardsDataSource = (DataSource) context.getBean("wizardsDataSource");
        DataSource deadWizardsDataSource = (DataSource) context.getBean("deadWizardsDataSource");
        DataSource passwordDataSource = (DataSource) context.getBean("passwordDataSource");
        EntityManagerFactory wizardsEntityManagerFactory = (EntityManagerFactory) context.getBean("wizardsEntityManagerFactory");
        EntityManagerFactory deadWizardsEntityManagerFactory = (EntityManagerFactory) context.getBean("deadWizardsEntityManagerFactory");
        EntityManagerFactory passwordEntityManagerFactory = (EntityManagerFactory) context.getBean("passwordEntityManagerFactory");

        assertNotNull(wizardsDataSource);
        assertNotNull(deadWizardsDataSource);
        assertNotNull(passwordDataSource);
        assertNotNull(wizardsEntityManagerFactory);
        assertNotNull(deadWizardsEntityManagerFactory);
        assertNotNull(passwordEntityManagerFactory);

    }

    @Test
    void correctDataBase(){
        DataSource wizardsDataSource = (DataSource) context.getBean("wizardsDataSource");
        DataSource deadWizardsDataSource = (DataSource) context.getBean("deadWizardsDataSource");
        DataSource passwordDataSource = (DataSource) context.getBean("passwordDataSource");

        String wizardsURL = ((HikariDataSource) wizardsDataSource).getJdbcUrl();
        String deadWizardsURL = ((HikariDataSource) deadWizardsDataSource).getJdbcUrl();
        String passwordURL = ((HikariDataSource) passwordDataSource).getJdbcUrl();

        assertEquals("jdbc:h2:mem:wizards", wizardsURL);
        assertEquals("jdbc:h2:mem:deletedWizards", deadWizardsURL);
        assertEquals("jdbc:h2:mem:password", passwordURL);
    }

    @Test
    void openDbTest() throws SQLException {
        DataSource wizardsDataSource = (DataSource) context.getBean("wizardsDataSource");
        DataSource deadWizardsDataSource = (DataSource) context.getBean("deadWizardsDataSource");
        DataSource passwordDataSource = (DataSource) context.getBean("passwordDataSource");

        assertFalse(wizardsDataSource.getConnection().isClosed());
        assertFalse(deadWizardsDataSource.getConnection().isClosed());
        assertFalse(passwordDataSource.getConnection().isClosed());

    }


}
