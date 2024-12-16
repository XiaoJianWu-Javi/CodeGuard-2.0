package es.tfg.codeguard.model.entity;

import es.tfg.codeguard.model.entity.exercise.Exercise;
import es.tfg.codeguard.util.ExerciseDescriptionNotValid;
import es.tfg.codeguard.util.ExerciseSolutionNotValidException;
import es.tfg.codeguard.util.ExerciseTitleNotValidException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExerciseTests {

    private Exercise exercise;

    @BeforeEach
    void setup() {
        exercise = new Exercise("id", "title", "desc");
    }

    @Test
    void notValidSetTitle() {
        assertThrows(ExerciseTitleNotValidException.class, () -> exercise.setTitle(null));
        assertThrows(ExerciseTitleNotValidException.class, () -> exercise.setTitle(""));
        assertThrows(ExerciseTitleNotValidException.class, () -> exercise.setTitle("   "));
        assertThrows(ExerciseTitleNotValidException.class, () -> exercise.setTitle("\n"));
    }

    @Test
    void validSetTitle() {
        assertDoesNotThrow(() -> exercise.setTitle("aaa1"));
        assertDoesNotThrow(() -> exercise.setTitle("La Conjetura de Goldbach"));
    }

    @Test
    void notValidSetDescription() {
        assertThrows(ExerciseDescriptionNotValid.class, () -> exercise.setDescription(null));
        assertThrows(ExerciseDescriptionNotValid.class, () -> exercise.setDescription(""));
        assertThrows(ExerciseDescriptionNotValid.class, () -> exercise.setDescription("   "));
        assertThrows(ExerciseDescriptionNotValid.class, () -> exercise.setDescription("\n"));
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

    @Test
    void setAndGetSolutions() {
        assertEquals(0, exercise.getSolutions().size()); //Not Initialized

        assertThrows(ExerciseSolutionNotValidException.class,
                            () -> exercise.setSolutions(null));
        assertThrows(ExerciseSolutionNotValidException.class,
                            () -> exercise.setSolutions(new HashMap<String, String>() {{put(null, null);}}));
        assertThrows(ExerciseSolutionNotValidException.class,
                            () -> exercise.setSolutions(new HashMap<String, String>() {{put("", null);}}));
        assertThrows(ExerciseSolutionNotValidException.class,
                            () -> exercise.setSolutions(new HashMap<String, String>() {{put(null, "");}}));
        assertThrows(ExerciseSolutionNotValidException.class,
                            () -> exercise.setSolutions(new HashMap<String, String>() {{put("", ""); put(null, "");}}));

        Map<String, String> original = new HashMap<String, String>() {{put("", ""); put("", "");}};
        assertDoesNotThrow(() -> exercise.setSolutions(original));
        assertEquals(original.size(), exercise.getSolutions().size());
        assertArrayEquals(original.entrySet().toArray(), exercise.getSolutions().entrySet().toArray());
    }

    @Test
    void getSolvedTimesTest() {
        assertEquals(0, exercise.getSolvedTimes());
        Map<String, String> originalSolutions = new HashMap<String, String>() {{put("user1", ""); put("user2", "");}};
        exercise.setSolutions(originalSolutions);
        assertEquals(originalSolutions.size(), exercise.getSolvedTimes());
        exercise.addSolution("user3", "");
        assertEquals(originalSolutions.size() + 1, exercise.getSolvedTimes());
    }

    @Test
    void triedUsernameTest() {
        assertTrue(exercise.getTriedUsernames().isEmpty());
        exercise.addTriedUsername("user1");
        assertNotNull(exercise.getTriedUsernames());
        assertEquals(1, exercise.getTriedUsernames().size());
        exercise.addTriedUsername("user2");
        assertEquals(2, exercise.getTriedUsernames().size());
        exercise.addTriedUsername("user1");
        assertEquals(2, exercise.getTriedUsernames().size());
        exercise.removeTriedUsername("user1");
        assertEquals(1, exercise.getTriedUsernames().size());
    }

    @Test
    void solvedUsernameTest() {
        assertTrue(exercise.getSolvedUsernames().isEmpty());
        exercise.addSolvedUsername("user1");
        assertNotNull(exercise.getSolvedUsernames());
        assertEquals(1, exercise.getSolvedUsernames().size());
        exercise.addSolvedUsername("user2");
        assertEquals(2, exercise.getSolvedUsernames().size());
        exercise.addSolvedUsername("user1");
        assertEquals(2, exercise.getSolvedUsernames().size());
        exercise.removeSolvedUsername("user1");
        assertEquals(1, exercise.getSolvedUsernames().size());
    }
}
