package es.tfg.codeguard.model.entity;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserTests {

    private User user;

    @BeforeEach
    void setup() {
        user = new User();
    }

    @Test
    void notValidSetWizardName() {
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
    void validSetWizardName() {
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
    void getNotInitializedWizardName() {
        assertThrows(NoSuchElementException.class, () -> user.getUsername());
        try { user.setUsername(""); } catch(Exception e) {}
        assertThrows(NoSuchElementException.class, () -> user.getUsername());
    }

    @Test
    void getInitializedWizardName() {
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

//    @Test
//    void setExercisesTests() {
//        java.util.List<Exercise> exercises = new java.util.ArrayList<>() {{
//            add(null); add(new Exercise()); add(new Exercise());
//        }};
//        assertThrows(IllegalArgumentException.class, () -> user.setExercises(exercises));
//
//        spells.clear();
//        spells.add(new Exercise());
//        assertDoesNotThrow(() -> user.setExercises(exercises));
//    }

//    @Test
//    void getExercisesTests() {
//        assertTrue(user.getExercises().isEmpty());  //Not Initialized
//
//        java.util.List<Exercise> exercises = new java.util.ArrayList<>() {{
//            add(new Exercise()); add(new Exercise()); add(new Exercise());
//        }};
//        saruman.setExercises(exercises);
//        assertArrayEquals(exercises.toArray(), user.getExercises().toArray());
//        assertEquals(exercises.size(), user.getExercises().size());
//
//        saruman.getExercises().clear();
//        assertArrayEquals(exercises.toArray(), user.getExercises().toArray());
//        assertEquals(exercises.size(), user.getExercises().size());
//    }
}
