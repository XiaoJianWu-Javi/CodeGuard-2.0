package es.tfg.codeguard.model.entity;

import java.util.NoSuchElementException;

import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import es.tfg.codeguard.model.entity.user.User;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserTests {

    private User user;

    @BeforeEach
    void setup() {
        user = new User();
    }

    @Test
    void notValidSetUsername() {
        assertThrows(IllegalArgumentException.class, () -> user.setUsername(null));
        assertThrows(IllegalArgumentException.class, () -> user.setUsername(""));
        assertThrows(IllegalArgumentException.class, () -> user.setUsername("   "));
        assertThrows(IllegalArgumentException.class, () -> user.setUsername("a"));
        assertThrows(IllegalArgumentException.class, () -> user.setUsername("a.aa2"));
        assertThrows(IllegalArgumentException.class, () -> user.setUsername("2213"));
        assertThrows(IllegalArgumentException.class, () -> user.setUsername("a??asda"));
        assertThrows(IllegalArgumentException.class, () -> user.setUsername("a<a223e"));
        assertThrows(IllegalArgumentException.class, () -> user.setUsername("a1122"));
        assertThrows(IllegalArgumentException.class, () -> user.setUsername("1aaa"));
        assertThrows(IllegalArgumentException.class, () -> user.setUsername("aa324a1"));
        assertThrows(IllegalArgumentException.class, () -> user.setUsername("a1231aa1"));
    }
    
    @Test
    void validSetUsername() {
        assertDoesNotThrow(() -> user.setUsername("aaa1"));
        assertDoesNotThrow(() -> user.setUsername("aaa1aaa43535"));
        assertDoesNotThrow(() -> user.setUsername("AAA1"));
        assertDoesNotThrow(() -> user.setUsername("Aaa1"));
        assertDoesNotThrow(() -> user.setUsername("aaAA321"));
        assertDoesNotThrow(() -> user.setUsername("aaa1fewes"));
        assertDoesNotThrow(() -> user.setUsername("sarumanName"));
        assertDoesNotThrow(() -> user.setUsername("reaaa"));
    }

    @Test
    void getNotInitializedUsername() {
        assertThrows(NoSuchElementException.class, () -> user.getUsername());
        try { user.setUsername(""); } catch(Exception e) {}
        assertThrows(NoSuchElementException.class, () -> user.getUsername());
    }

    @Test
    void getInitializedUsername() {
        String sarumanName = "saruman";

        user.setUsername(sarumanName);
        assertEquals(sarumanName, user.getUsername());
        try { user.setUsername(""); } catch(Exception e) {}
        assertEquals(sarumanName, user.getUsername());
    }

    @Test
    void isAndSetTesterOrCreator() {
        assertFalse(user.isTester());    //Not Initialized
        assertFalse(user.isCreator());   //Not Initialized

        user.setTester(true);
        assertTrue(user.isTester());
        assertFalse(user.isCreator());

        user.setCreator(true);
        user.setTester(false);
        assertFalse(user.isTester());
        assertTrue(user.isCreator());

        user.setTester(true);
        assertTrue(user.isTester());
        assertTrue(user.isCreator());
    }

    @Test
    void setExercisesTests() {
        java.util.List<String> exercises = new java.util.ArrayList<>() {{
            add(null); add("problema-1"); add("problema-14");
        }};
        assertThrows(IllegalArgumentException.class, () -> user.setExercises(exercises));

        exercises.clear();
        exercises.add("problema-1");
        assertDoesNotThrow(() -> user.setExercises(exercises));
    }

    @Test
    void getExercisesTests() {
        assertTrue(user.getExercises().isEmpty());  //Not Initialized

        java.util.List<String> exercises = new java.util.ArrayList<>() {{
            add("problema-16"); add("problema-2"); add("problema-1");
        }};
        user.setExercises(exercises);
        assertArrayEquals(exercises.toArray(), user.getExercises().toArray());
        assertEquals(exercises.size(), user.getExercises().size());

        user.getExercises().clear();
        assertArrayEquals(exercises.toArray(), user.getExercises().toArray());
        assertEquals(exercises.size(), user.getExercises().size());
    }
}
