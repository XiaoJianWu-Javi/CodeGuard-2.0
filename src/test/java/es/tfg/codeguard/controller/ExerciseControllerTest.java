package es.tfg.codeguard.controller;

import es.tfg.codeguard.controller.imp.ExerciseControllerImp;
import es.tfg.codeguard.controller.imp.UserControllerImp;
import es.tfg.codeguard.model.dto.ExerciseDTO;
import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.service.AdminService;
import es.tfg.codeguard.service.ExerciseService;
import es.tfg.codeguard.service.UserService;
import es.tfg.codeguard.util.ExerciseNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.Optional;

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

    @BeforeEach
    void setup() {


    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4"})
    void GetExerciseByIdTest(String exerciseId) {

        ExerciseDTO exerciseDTO = new ExerciseDTO(exerciseId, "title" + exerciseId, "description" + exerciseId, "tester" + exerciseId, "creator" + exerciseId);

        when(exerciseService.getExerciseById(exerciseId)).thenReturn(exerciseDTO);

        ExerciseDTO resultado = exerciseService.getExerciseById(exerciseId);

        ResponseEntity<ExerciseDTO> esperado = exerciseControllerImp.getExercise(exerciseId);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.OK));

    }

    @ParameterizedTest
    @ValueSource(strings = {"1p", "2ter", "t30", "a4", "54uri"})
    void NotFoundExerciseById(String exerciseId) {

        when(exerciseService.getExerciseById(exerciseId)).thenThrow(new ExerciseNotFoundException("Exercise not found [" +exerciseId +"]"));

        ResponseEntity<ExerciseDTO> esperado = exerciseControllerImp.getExercise(exerciseId);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @Test
    void GetAllExercisesTest() {

        ExerciseDTO exercise1 = new ExerciseDTO("1", "title" + 1, "description" + 1, "tester" + 1, "creator" + 1);
        ExerciseDTO exercise2 = new ExerciseDTO("2", "title" + 2, "description" + 2, "tester" + 2, "creator" + 2);
        ExerciseDTO exercise3 = new ExerciseDTO("3", "title" + 3, "description" + 3, "tester" + 3, "creator" + 3);


        when(exerciseService.getAllExercises()).thenReturn(new ArrayList<ExerciseDTO>(Arrays.asList()));

        List<ExerciseDTO> resultado = exerciseService.getAllExercises();

        ResponseEntity<List<ExerciseDTO>> esperado = exerciseControllerImp.getAllExercises();

        Assertions.assertEquals(0, resultado.size());
        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.OK));


        when(exerciseService.getAllExercises()).thenReturn(new ArrayList<ExerciseDTO>(Arrays.asList(exercise1)));

        resultado = exerciseService.getAllExercises();

        esperado = exerciseControllerImp.getAllExercises();

        Assertions.assertEquals(1, resultado.size());
        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.OK));


        when(exerciseService.getAllExercises()).thenReturn(new ArrayList<ExerciseDTO>(Arrays.asList(exercise1, exercise2)));

        resultado = exerciseService.getAllExercises();

        esperado = exerciseControllerImp.getAllExercises();

        Assertions.assertEquals(2, resultado.size());
        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.OK));


        when(exerciseService.getAllExercises()).thenReturn(new ArrayList<ExerciseDTO>(Arrays.asList(exercise1, exercise2, exercise3)));

        resultado = exerciseService.getAllExercises();

        esperado = exerciseControllerImp.getAllExercises();

        Assertions.assertEquals(3, resultado.size());
        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.OK));

    }


}
