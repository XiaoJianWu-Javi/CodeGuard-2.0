package es.tfg.codeguard.service;

import es.tfg.codeguard.model.dto.ExerciseDTO;
import es.tfg.codeguard.model.dto.SolutionDTO;
import es.tfg.codeguard.model.entity.exercise.Exercise;
import es.tfg.codeguard.model.repository.deleteduser.DeletedUserRepository;
import es.tfg.codeguard.model.repository.exercise.ExerciseRepository;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.service.imp.ExerciseServiceImp;
import es.tfg.codeguard.util.ExerciseNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ExerciseServiceTest {

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private UserPassRepository userPassRepository;

    @Mock
    private DeletedUserRepository deletedUserRepository;
    
    @InjectMocks
    private ExerciseServiceImp exerciseServiceImp;

    @Test
    public void testFineGetAllExercises() {

        Exercise exercise1 = new Exercise();
        exercise1.setTitle("tittle1");
        exercise1.setDescription("description1");
        exercise1.setTest("tester1");
        exercise1.setCreator("creator1");

        Exercise exercise2 = new Exercise();
        exercise2.setTitle("tittle2");
        exercise2.setDescription("description2");
        exercise2.setTest("tester2");
        exercise2.setCreator("creator2");

        Exercise exercise3 = new Exercise();
        exercise3.setTitle("tittle3");
        exercise3.setDescription("description3");
        exercise3.setTest("tester3");
        exercise3.setCreator("creator3");


        List<Exercise> exercisesExpected = List.of(exercise1, exercise2, exercise3);

        when(exerciseRepository.findAll()).thenReturn(exercisesExpected);

        List<ExerciseDTO> exercises = exerciseServiceImp.getAllExercises();

        assertThat(exercisesExpected.stream().map(ExerciseDTO::new)).usingRecursiveComparison().isEqualTo(exercises);

    }

    @Test
    public void testFailGetAllExercises() {


        List<Exercise> exercisesExpected = Collections.emptyList();

        when(exerciseRepository.findAll()).thenReturn(exercisesExpected);

        List<ExerciseDTO> exercises = exerciseServiceImp.getAllExercises();

        assertThat(exercisesExpected.stream().map(ExerciseDTO::new)).usingRecursiveComparison().isEqualTo(exercises);

    }

    @Test
    public void testFailGetExerciseByName() {
        when(exerciseRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(ExerciseNotFoundException.class, () -> exerciseServiceImp.getExerciseById("1"));
    }

    @Test
    public void testFineGetExerciseByName() {

        Exercise exercise = new Exercise();
        exercise.setTitle("tittle1");
        exercise.setDescription("description1");
        exercise.setTest("tester1");
        exercise.setCreator("creator1");
        

        when(exerciseRepository.findById("1")).thenReturn(Optional.of(exercise));

        ExerciseDTO expectedExercise = new ExerciseDTO(exercise);
        ExerciseDTO actualExercise = exerciseServiceImp.getExerciseById("1");

        assertThat(expectedExercise).usingRecursiveComparison().isEqualTo(actualExercise);
    }
    
    @Test
    public void testFineGetAllSolutionsForExercise() {
    	
    	java.util.Map<String, String> solutions = new java.util.HashMap<String, String>() {{put("user", "solution");put("user2", "solution");}};

        Exercise exercise = new Exercise("1", "titulo", "desc");
        exercise.setSolutions(solutions);

        List<SolutionDTO> expectedSolutions = solutions.entrySet().stream()
        															.map(entry -> new SolutionDTO("1", entry.getKey(), entry.getValue()))
        															.toList();
        
        when(exerciseRepository.findById("1")).thenReturn(Optional.of(exercise));

        List<SolutionDTO> actualSolutions = exerciseServiceImp.getAllSolutionsForExercise("1");

        assertArrayEquals(expectedSolutions.toArray(),actualSolutions.toArray());
    }
    
    
    @Test
    public void testFailGetAllSolutionsForExercise() {

    	Exercise exercise = new Exercise("1", "titulo", "desc");

        List<Exercise> expectedSolutions = Collections.emptyList();

        when(exerciseRepository.findById("1")).thenReturn(Optional.of(exercise));
        
        List<SolutionDTO> actualSolutions = exerciseServiceImp.getAllSolutionsForExercise("1");

        assertArrayEquals(expectedSolutions.toArray(), actualSolutions.toArray());

    }
    
    @Test
    public void testGetUserSolutionForExercise() {
    	
    	java.util.Map<String, String> solutions = new java.util.HashMap<String, String>() {{put("user", "solution");put("user2", "solution");}};

        Exercise exercise = new Exercise("1", "titulo", "desc");
        exercise.setSolutions(solutions);
        
        when(exerciseRepository.findById("1")).thenReturn(Optional.of(exercise));
        
        SolutionDTO expectedSolution = new SolutionDTO(exercise.getId(), "user2", solutions.get("user2"));
        
        assertThat(expectedSolution).usingRecursiveComparison().isEqualTo(exerciseServiceImp.getUserSolutionForExercise("user2", "1"));
    }
    
    @Test
    public void testFailGetUserSolutionForExercise() {
    	Exercise exercise = new Exercise("1", "titulo", "desc");
        when(exerciseRepository.findById("1")).thenReturn(Optional.empty());
        when(exerciseRepository.findById("2")).thenReturn(Optional.of(exercise));

        assertThrows(ExerciseNotFoundException.class, () -> exerciseServiceImp.getUserSolutionForExercise("user", "1"));
        assertThrows(NoSuchElementException.class, () -> exerciseServiceImp.getUserSolutionForExercise("user", "2"));
    }
}
