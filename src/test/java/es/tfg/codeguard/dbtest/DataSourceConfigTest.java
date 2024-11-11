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

        DataSource usersDataSource = (DataSource) context.getBean("usersDataSource");
        DataSource deletedUsersDataSource = (DataSource) context.getBean("deletedUsersDataSource");
        DataSource passwordDataSource = (DataSource) context.getBean("passwordDataSource");
        EntityManagerFactory usersEntityManagerFactory = (EntityManagerFactory) context.getBean("entityManagerFactoryUsers");
        EntityManagerFactory deletedUsersEntityManagerFactory = (EntityManagerFactory) context.getBean("entityManagerFactoryDeletedUsers");
        EntityManagerFactory passwordEntityManagerFactory = (EntityManagerFactory) context.getBean("entityManagerFactoryPassword");

        assertNotNull(usersDataSource);
        assertNotNull(deletedUsersDataSource);
        assertNotNull(passwordDataSource);
        assertNotNull(usersEntityManagerFactory);
        assertNotNull(deletedUsersEntityManagerFactory);
        assertNotNull(passwordEntityManagerFactory);

    }

    @Test
    void correctDataBase(){
        DataSource usersDataSource = (DataSource) context.getBean("usersDataSource");
        DataSource deletedUsersDataSource = (DataSource) context.getBean("deletedUsersDataSource");
        DataSource passwordDataSource = (DataSource) context.getBean("passwordDataSource");

        String usersURL = ((HikariDataSource) usersDataSource).getJdbcUrl();
        String deletedUsersURL = ((HikariDataSource) deletedUsersDataSource).getJdbcUrl();
        String passwordURL = ((HikariDataSource) passwordDataSource).getJdbcUrl();

        assertEquals("jdbc:h2:mem:wizards", usersURL);
        assertEquals("jdbc:h2:mem:deletedWizards", deletedUsersURL);
        assertEquals("jdbc:h2:mem:password", passwordURL);
    }

    @Test
    void openDbTest() throws SQLException {
        DataSource usersDataSource = (DataSource) context.getBean("usersDataSource");
        DataSource deletedUsersDataSource = (DataSource) context.getBean("deletedUsersDataSource");
        DataSource passwordDataSource = (DataSource) context.getBean("passwordDataSource");

        assertFalse(usersDataSource.getConnection().isClosed());
        assertFalse(deletedUsersDataSource.getConnection().isClosed());
        assertFalse(passwordDataSource.getConnection().isClosed());
    }
}
