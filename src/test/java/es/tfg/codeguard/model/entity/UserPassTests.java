package es.tfg.codeguard.model.entity;

import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.util.UsernameNotValidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserPassTests {

    private UserPass userPass;

    @BeforeEach
    void setup() {
        userPass = new UserPass();
    }

    @Test
    void notValidSetUsername() {
        assertThrows(UsernameNotValidException.class, () -> userPass.setUsername(null));
        assertThrows(UsernameNotValidException.class, () -> userPass.setUsername(""));
        assertThrows(UsernameNotValidException.class, () -> userPass.setUsername("   "));
        assertThrows(UsernameNotValidException.class, () -> userPass.setUsername("\n"));
        assertThrows(UsernameNotValidException.class, () -> userPass.setUsername("a"));
        assertThrows(UsernameNotValidException.class, () -> userPass.setUsername("a.aa2"));
        assertThrows(UsernameNotValidException.class, () -> userPass.setUsername("2213"));
        assertThrows(UsernameNotValidException.class, () -> userPass.setUsername("a??asda"));
        assertThrows(UsernameNotValidException.class, () -> userPass.setUsername("a<a223e"));
        assertThrows(UsernameNotValidException.class, () -> userPass.setUsername("a1122"));
        assertThrows(UsernameNotValidException.class, () -> userPass.setUsername("1aaa"));
        assertThrows(UsernameNotValidException.class, () -> userPass.setUsername("aa324a1"));
        assertThrows(UsernameNotValidException.class, () -> userPass.setUsername("a1231aa1"));
    }

    @Test
    void validSetUsername() {
        assertDoesNotThrow(() -> userPass.setUsername("aaa1"));
        assertDoesNotThrow(() -> userPass.setUsername("aaa1aaa43535"));
        assertDoesNotThrow(() -> userPass.setUsername("AAA1"));
        assertDoesNotThrow(() -> userPass.setUsername("Aaa1"));
        assertDoesNotThrow(() -> userPass.setUsername("aaAA321"));
        assertDoesNotThrow(() -> userPass.setUsername("aaa1fewes"));
        assertDoesNotThrow(() -> userPass.setUsername("sarumanName"));
        assertDoesNotThrow(() -> userPass.setUsername("reaaa"));
    }

    @Test
    void getNotInitializedUsername() {
        assertThrows(NoSuchElementException.class, () -> userPass.getUsername());
        try {
            userPass.setUsername("");
        } catch (Exception e) {
        }
        assertThrows(NoSuchElementException.class, () -> userPass.getUsername());
    }

    @Test
    void getInitializedUsername() {
        String sarumanName = "saruman";

        userPass.setUsername(sarumanName);
        assertEquals(sarumanName, userPass.getUsername());
        try {
            userPass.setUsername("");
        } catch (Exception e) {
        }
        assertEquals(sarumanName, userPass.getUsername());
    }

    @Test
    void isAndSetElder() {
        assertFalse(userPass.isAdmin());    //Not Initialized

        userPass.setAdmin(true);
        assertTrue(userPass.isAdmin());

        userPass.setAdmin(false);
        assertFalse(userPass.isAdmin());
    }

    @Test
    void notValidSetHashedPass() {
        assertThrows(IllegalArgumentException.class, () -> userPass.setHashedPass(null));
        assertThrows(IllegalArgumentException.class, () -> userPass.setHashedPass(""));
        assertThrows(IllegalArgumentException.class, () -> userPass.setHashedPass("   "));
        assertThrows(IllegalArgumentException.class, () -> userPass.setHashedPass("\n"));
    }

    @Test
    void validSetHashedPass() {
        assertDoesNotThrow(() -> userPass.setHashedPass("11111111111111"));
        assertDoesNotThrow(() -> userPass.setHashedPass("83874fo8w7ycoiureovq'¡'¡´+´12+`3ç"));
    }

    @Test
    void getNotInitializedHashedPass() {
        assertThrows(NoSuchElementException.class, () -> userPass.getHashedPass());
        try {
            userPass.setHashedPass("");
        } catch (Exception e) {
        }
        assertThrows(NoSuchElementException.class, () -> userPass.getHashedPass());
    }

    @Test
    void getInitializedHashedPass() {
        String hashedPass = "s4rum4n";

        userPass.setHashedPass(hashedPass);
        assertEquals(hashedPass, userPass.getHashedPass());
        try {
            userPass.setHashedPass("");
        } catch (Exception e) {
        }
        assertEquals(hashedPass, userPass.getHashedPass());
    }
}
