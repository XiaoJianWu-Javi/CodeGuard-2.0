package es.tfg.codeguard.controller;

import es.tfg.codeguard.controller.imp.ExerciseControllerImp;
import es.tfg.codeguard.model.dto.ExerciseDTO;
import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.service.AdminService;
import es.tfg.codeguard.service.ExerciseService;
import es.tfg.codeguard.util.ExerciseNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ExerciseControllerTest {

    @Mock
    private ExerciseService exerciseService;

    @Mock
    private AdminService adminService;

    @InjectMocks
    private ExerciseControllerImp exerciseControllerImp;

    private UserDTO userDTO;
    private UserPassDTO userPassDTO;

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4"})
    void getExerciseByIdTest(String exerciseId) {

        ExerciseDTO exerciseDTO = new ExerciseDTO(exerciseId, "title" + exerciseId, "description" + exerciseId, "tester" + exerciseId, "creator" + exerciseId);

        when(exerciseService.getExerciseById(exerciseId)).thenReturn(exerciseDTO);

        ExerciseDTO expected = exerciseService.getExerciseById(exerciseId);

        ResponseEntity<ExerciseDTO> result = exerciseControllerImp.getExercise(exerciseId);

        assertThat(new ResponseEntity<>(expected, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(result);

    }

    @ParameterizedTest
    @ValueSource(strings = {"1p", "2ter", "t30", "a4", "54uri"})
    void getExerciseByIdTestExerciseNotFound(String exerciseId) {

        when(exerciseService.getExerciseById(exerciseId)).thenThrow(new ExerciseNotFoundException("Exercise not found [" + exerciseId + "]"));

        ResponseEntity<ExerciseDTO> result = exerciseControllerImp.getExercise(exerciseId);

        assertThat(new ResponseEntity<>(HttpStatus.NOT_FOUND)).usingRecursiveComparison().isEqualTo(result);

    }

    @Test
    void getAllExercisesTest() {

        ExerciseDTO exercise1 = new ExerciseDTO("valid-braces", "Valid Braces", "description" + 1, "tester" + 1, "creator" + 1);
        ExerciseDTO exercise2 = new ExerciseDTO("reverse-words", "Reverse Words" + 2, "description" + 2, "tester" + 2, "creator" + 2);
        ExerciseDTO exercise3 = new ExerciseDTO("build-square", "Build Square", "description" + 3, "tester" + 3, "creator" + 3);


        when(exerciseService.getAllExercises()).thenReturn(new ArrayList<ExerciseDTO>(Arrays.asList()));

        List<ExerciseDTO> expected = exerciseService.getAllExercises();

        ResponseEntity<List<ExerciseDTO>> result = exerciseControllerImp.getAllExercises();

        Assertions.assertEquals(0, result.getBody().size());
        assertThat(new ResponseEntity<>(expected, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(result);


        when(exerciseService.getAllExercises()).thenReturn(new ArrayList<ExerciseDTO>(Arrays.asList(exercise1)));

        expected = exerciseService.getAllExercises();

        result = exerciseControllerImp.getAllExercises();

        Assertions.assertEquals(1, result.getBody().size());
        assertThat(new ResponseEntity<>(expected, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(result);


        when(exerciseService.getAllExercises()).thenReturn(new ArrayList<ExerciseDTO>(Arrays.asList(exercise1, exercise2)));

        expected = exerciseService.getAllExercises();

        result = exerciseControllerImp.getAllExercises();

        Assertions.assertEquals(2, result.getBody().size());
        assertThat(new ResponseEntity<>(expected, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(result);


        when(exerciseService.getAllExercises()).thenReturn(new ArrayList<ExerciseDTO>(Arrays.asList(exercise1, exercise2, exercise3)));

        expected = exerciseService.getAllExercises();

        result = exerciseControllerImp.getAllExercises();

        Assertions.assertEquals(3, result.getBody().size());
        assertThat(new ResponseEntity<>(expected, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(result);

    }

    @Test
    void getAllExercisesPaginatedTest() {

        ExerciseDTO exercise1 = new ExerciseDTO("project-euler-9", "Project Euler 9", "description" + 1, "tester" + 1, "creator" + 1);
        ExerciseDTO exercise2 = new ExerciseDTO("saruman-123", "La Traición de Isengard", "description" + 2, "tester" + 2, "creator" + 2);
        ExerciseDTO exercise3 = new ExerciseDTO("magic-music-box", "Magic Music Box", "description" + 3, "tester" + 3, "creator" + 3);

        when(exerciseService.getAllExercisesPaginated("Je", 1, false)).thenReturn(new ArrayList<ExerciseDTO>(Arrays.asList()));

        List<ExerciseDTO> expected = exerciseService.getAllExercisesPaginated("Je", 1, false);

        ResponseEntity<List<ExerciseDTO>> result = exerciseControllerImp.getAllExercisesPaginated("Je", 1, false);

        Assertions.assertEquals(0, result.getBody().size());
        assertThat(new ResponseEntity<>(expected, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(result);


        when(exerciseService.getAllExercisesPaginated("je", 1, false)).thenReturn(new ArrayList<ExerciseDTO>(Arrays.asList(exercise1)));

        expected = exerciseService.getAllExercisesPaginated("je", 1, false);

        result = exerciseControllerImp.getAllExercisesPaginated("je", 1, false);

        Assertions.assertEquals(1, result.getBody().size());
        assertThat(new ResponseEntity<>(expected, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(result);


        when(exerciseService.getAllExercisesPaginated("g", 0, false)).thenReturn(new ArrayList<ExerciseDTO>(Arrays.asList(exercise2, exercise3)));

        expected = exerciseService.getAllExercisesPaginated("g", 0, false);

        result = exerciseControllerImp.getAllExercisesPaginated("g", 0, false);

        Assertions.assertEquals(2, result.getBody().size());
        assertThat(new ResponseEntity<>(expected, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(result);


        when(exerciseService.getAllExercisesPaginated("g", 0, true)).thenReturn(new ArrayList<ExerciseDTO>(Arrays.asList(exercise3, exercise2)));

        expected = exerciseService.getAllExercisesPaginated("g", 0, true);

        result = exerciseControllerImp.getAllExercisesPaginated("g", 0, true);

        Assertions.assertEquals(2, result.getBody().size());
        assertThat(new ResponseEntity<>(expected, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(result);

    }

}