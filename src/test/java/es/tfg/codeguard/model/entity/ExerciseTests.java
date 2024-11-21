package es.tfg.codeguard.model.entity;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import es.tfg.codeguard.model.entity.exercise.Exercise;

@SpringBootTest
class ExerciseTests {

    private Exercise exercise;

    @BeforeEach
    void setup() {
        exercise = new Exercise("title", "desc");
    }

    @Test
    void notValidSetTitle() {
        assertThrows(IllegalArgumentException.class, () -> exercise.setTitle(null));
        assertThrows(IllegalArgumentException.class, () -> exercise.setTitle(""));
        assertThrows(IllegalArgumentException.class, () -> exercise.setTitle("   "));
        assertThrows(IllegalArgumentException.class, () -> exercise.setTitle("\n"));
    }
    
    @Test
    void validSetTitle() {
        assertDoesNotThrow(() -> exercise.setTitle("aaa1"));
        assertDoesNotThrow(() -> exercise.setTitle("La Conjetura de Goldbach"));
    }

    @Test
    void notValidSetDescription() {
        assertThrows(IllegalArgumentException.class, () -> exercise.setDescription(null));
        assertThrows(IllegalArgumentException.class, () -> exercise.setDescription(""));
        assertThrows(IllegalArgumentException.class, () -> exercise.setDescription("   "));
        assertThrows(IllegalArgumentException.class, () -> exercise.setDescription("\n"));
    }
    
    @Test
    void validSetDescription() {
        assertDoesNotThrow(() -> exercise.setDescription("a"));
        assertDoesNotThrow(() -> exercise.setDescription("Este es un problema. Con punto"));
        assertDoesNotThrow(() -> exercise.setDescription("Y este es un problema. Con punto\ny salto de línea"));
    }

    @Test
    void getAndSetValidTest() {
        assertNull(exercise.getTest()); //Not Initialized

        String expected = "import org.junit.jupiter.api.Test;\timport static org.junit.jupiter.api.Assertions.assertTrue;\tclass Testing {\t@Test\tvoid testMethod {\tassertTrue(true);\t}\t}";

        exercise.setTest(expected);
        assertEquals(expected, exercise.getTest());
    }

    @Test
    void getAndSetTesterOrCreator() {
        assertNull(exercise.getTester()); //Not Initialized
        assertNull(exercise.getCreator()); //Not Initialized

        String expected = "Saruman";

        exercise.setTester("Saruman");
        assertEquals(expected, exercise.getTester());
        assertNull(exercise.getCreator());

        exercise.setCreator("Saruman");
        assertEquals(expected, exercise.getTester());
        assertEquals(expected, exercise.getCreator());
    }
}
