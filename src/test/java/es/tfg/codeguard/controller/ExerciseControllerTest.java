package es.tfg.codeguard.controller;

import es.tfg.codeguard.controller.imp.ExerciseControllerImp;
import es.tfg.codeguard.model.dto.ExerciseDTO;
import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.service.AdminService;
import es.tfg.codeguard.service.ExerciseService;
import es.tfg.codeguard.util.ExerciseAlreadyExistException;
import es.tfg.codeguard.util.ExerciseDescriptionNotValid;
import es.tfg.codeguard.util.ExerciseNotFoundException;
import es.tfg.codeguard.util.ExerciseTitleNotValidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

        when(exerciseService.getExerciseById(exerciseId)).thenThrow(new ExerciseNotFoundException("Exercise not found [" + exerciseId + "]"));

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

    @Test
    void GetAllExercisesPaginatedTest() {

        ExerciseDTO exercise1 = new ExerciseDTO("project-euler-9", "Project Euler 9", "description" + 1, "tester" + 1, "creator" + 1);
        ExerciseDTO exercise2 = new ExerciseDTO("saruman-123", "La Traición de Isengard", "description" + 2, "tester" + 2, "creator" + 2);
        ExerciseDTO exercise3 = new ExerciseDTO("magic-music-box", "Magic Music Box", "description" + 3, "tester" + 3, "creator" + 3);


        when(exerciseService.getAllExercisesPaginated("Je",1,false)).thenReturn(new ArrayList<ExerciseDTO>(Arrays.asList()));

        List<ExerciseDTO> resultado = exerciseService.getAllExercisesPaginated("Je",1,false);

        ResponseEntity<List<ExerciseDTO>> esperado = exerciseControllerImp.getAllExercisesPaginated("Je",1,false);

        Assertions.assertEquals(0, resultado.size());
        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.OK));


        when(exerciseService.getAllExercisesPaginated("je",1,false)).thenReturn(new ArrayList<ExerciseDTO>(Arrays.asList(exercise1)));

        resultado = exerciseService.getAllExercisesPaginated("je",1,false);

        esperado = exerciseControllerImp.getAllExercisesPaginated("je",1,false);

        Assertions.assertEquals(1, resultado.size());
        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.OK));


        when(exerciseService.getAllExercisesPaginated("g",0,false)).thenReturn(new ArrayList<ExerciseDTO>(Arrays.asList(exercise2, exercise3)));

        resultado = exerciseService.getAllExercisesPaginated("g",0,false);

        esperado = exerciseControllerImp.getAllExercisesPaginated("g",0,false);

        Assertions.assertEquals(2, resultado.size());
        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.OK));


        when(exerciseService.getAllExercisesPaginated("g",0,true)).thenReturn(new ArrayList<ExerciseDTO>(Arrays.asList(exercise3, exercise2)));

        resultado = exerciseService.getAllExercisesPaginated("g",0,true);

        esperado = exerciseControllerImp.getAllExercisesPaginated("g",0,true);

        Assertions.assertEquals(2, resultado.size());
        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.OK));

    }

    @ParameterizedTest
    @CsvSource({
            "Houdini,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJIb3VkaW5pIiwiaWF0IjoxNzMyNzc3OTkzLCJleHAiOjE3MzI4NjQzOTN9.BIOCXkd2KpiwBbaiv1lOt-gs5oDFQMF7dmei7MuWw4w,Magic Music Box,\"The Magic Music Box exercise involves simulating a music box\",magic-music-box",
            "Rachel,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJSYWNoZWwiLCJpYXQiOjE3MzI3Nzg1NTEsImV4cCI6MTczMjg2NDk1MX0.Q-hQgnLCHAeLiG2mAjnaXb5ftQLfmis75jbvbLBFMlA,Build Square,\"I will give you an integer. Give me back a shape that is as long and wide as the integer\",build-square",
            "Blaise,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJCbGFpc2UiLCJpYXQiOjE3MzI3Nzg5OTgsImV4cCI6MTczMjg2NTM5OH0.B2ueU8_beww-gYbobXn2aqdcDvDdxb6xcIsEwuSyEFw,Delta Bits,\"Complete the function to determine the number of bits required to convert integer A to integer B (where A and B >= 0)\",delta-bits",
            "Albus,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJBbGJ1cyIsImlhdCI6MTczMjc3OTE3NywiZXhwIjoxNzMyODY1NTc3fQ.9jTa2oHY9-KQlwXzBIIydCaIkimjDBZzomGIPlryYqk,Reverse words,\"Complete the function that accepts a string parameter, and reverses each word in the string\",reverse-words"
    })
    void CreateExerciseTest(String username, String userToken, String exerciseTitle, String exerciseDescription, String exerciseId){


        when(exerciseService.createExercise(userToken, exerciseTitle, exerciseDescription)).thenReturn(new ExerciseDTO(exerciseId,exerciseTitle, exerciseDescription,"",username));

        ExerciseDTO expectedExercise = exerciseService.createExercise(userToken, exerciseTitle,exerciseDescription);

        ResponseEntity<ExerciseDTO> result = exerciseControllerImp.createExercise(userToken,exerciseTitle,exerciseDescription);

        assertThat(new ResponseEntity<>(expectedExercise, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(result);

    }

    @CsvSource({
            "Houdini,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJIb3VkaW5pIiwiaWF0IjoxNzMyNzc3OTkzLCJleHAiOjE3MzI4NjQzOTN9.BIOCXkd2KpiwBbaiv1lOt-gs5oDFQMF7dmei7MuWw4w,,\"The Magic Music Box exercise involves simulating a music box\",project-euler-9",
            "Rachel,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJSYWNoZWwiLCJpYXQiOjE3MzI3Nzg1NTEsImV4cCI6MTczMjg2NDk1MX0.Q-hQgnLCHAeLiG2mAjnaXb5ftQLfmis75jbvbLBFMlA,,\"I will give you an integer. Give me back a shape that is as long and wide as the integer\",project-euler-2",
            "Blaise,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJCbGFpc2UiLCJpYXQiOjE3MzI3Nzg5OTgsImV4cCI6MTczMjg2NTM5OH0.B2ueU8_beww-gYbobXn2aqdcDvDdxb6xcIsEwuSyEFw,,\"Write a function that takes a string of braces, and determines if the order of the braces is valid\",valid-braces",
            "Albus,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJBbGJ1cyIsImlhdCI6MTczMjc3OTE3NywiZXhwIjoxNzMyODY1NTc3fQ.9jTa2oHY9-KQlwXzBIIydCaIkimjDBZzomGIPlryYqk,,\"We need a simple function that determines if a plural is needed or not\",plural"
    })
    void CreateExerciseFailTest(String username, String userToken, String exerciseTitle, String exerciseDescription, String exerciseId){

        when(exerciseService.createExercise(userToken, exerciseTitle, exerciseDescription)).thenThrow(ExerciseTitleNotValidException.class);

        ExerciseDTO expectedExercise = exerciseService.createExercise(userToken, exerciseTitle,exerciseDescription);

        ResponseEntity<ExerciseDTO> result = exerciseControllerImp.createExercise(userToken,exerciseTitle,exerciseDescription);

        assertThat(new ResponseEntity<>(expectedExercise, HttpStatus.BAD_REQUEST)).usingRecursiveComparison().isEqualTo(result);

    }


    @ParameterizedTest
    @CsvSource({
            "Houdini,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJIb3VkaW5pIiwiaWF0IjoxNzMyNzc3OTkzLCJleHAiOjE3MzI4NjQzOTN9.BIOCXkd2KpiwBbaiv1lOt-gs5oDFQMF7dmei7MuWw4w,,magic-music-box",
            "Rachel,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJSYWNoZWwiLCJpYXQiOjE3MzI3Nzg1NTEsImV4cCI6MTczMjg2NDk1MX0.Q-hQgnLCHAeLiG2mAjnaXb5ftQLfmis75jbvbLBFMlA,Build Square,,build-square",
            "Blaise,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJCbGFpc2UiLCJpYXQiOjE3MzI3Nzg5OTgsImV4cCI6MTczMjg2NTM5OH0.B2ueU8_beww-gYbobXn2aqdcDvDdxb6xcIsEwuSyEFw,Delta Bits,,delta-bits",
            "Albus,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJBbGJ1cyIsImlhdCI6MTczMjc3OTE3NywiZXhwIjoxNzMyODY1NTc3fQ.9jTa2oHY9-KQlwXzBIIydCaIkimjDBZzomGIPlryYqk,Reverse words,,reverse-words"
    })
    void CreateExerciseDescriptionNotValidTest(String username, String userToken, String exerciseTitle, String exerciseDescription, String exerciseId){

        when(exerciseService.createExercise(userToken, exerciseTitle, exerciseDescription)).thenThrow(ExerciseDescriptionNotValid.class);

        ExerciseDTO expectedExercise = exerciseService.createExercise(userToken, exerciseTitle,exerciseDescription);

        ResponseEntity<ExerciseDTO> result = exerciseControllerImp.createExercise(userToken,exerciseTitle,exerciseDescription);

        assertThat(new ResponseEntity<>(expectedExercise, HttpStatus.BAD_REQUEST)).usingRecursiveComparison().isEqualTo(result);

    }


    @CsvSource({
            "Houdini,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJIb3VkaW5pIiwiaWF0IjoxNzMyNzc3OTkzLCJleHAiOjE3MzI4NjQzOTN9.BIOCXkd2KpiwBbaiv1lOt-gs5oDFQMF7dmei7MuWw4w,,\"The Magic Music Box exercise involves simulating a music box\",project-euler-9",
            "Rachel,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJSYWNoZWwiLCJpYXQiOjE3MzI3Nzg1NTEsImV4cCI6MTczMjg2NDk1MX0.Q-hQgnLCHAeLiG2mAjnaXb5ftQLfmis75jbvbLBFMlA,,\"I will give you an integer. Give me back a shape that is as long and wide as the integer\",project-euler-2",
            "Blaise,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJCbGFpc2UiLCJpYXQiOjE3MzI3Nzg5OTgsImV4cCI6MTczMjg2NTM5OH0.B2ueU8_beww-gYbobXn2aqdcDvDdxb6xcIsEwuSyEFw,,\"Write a function that takes a string of braces, and determines if the order of the braces is valid\",valid-braces",
            "Albus,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJBbGJ1cyIsImlhdCI6MTczMjc3OTE3NywiZXhwIjoxNzMyODY1NTc3fQ.9jTa2oHY9-KQlwXzBIIydCaIkimjDBZzomGIPlryYqk,,\"We need a simple function that determines if a plural is needed or not\",plural"
    })
    void CreateExerciseAlredyExistTest(String username, String userToken, String exerciseTitle, String exerciseDescription, String exerciseId){

        when(exerciseService.createExercise(userToken, exerciseTitle, exerciseDescription)).thenThrow(ExerciseAlreadyExistException.class);

        ExerciseDTO expectedExercise = exerciseService.createExercise(userToken, exerciseTitle,exerciseDescription);

        ResponseEntity<ExerciseDTO> result = exerciseControllerImp.createExercise(userToken,exerciseTitle,exerciseDescription);

        assertThat(new ResponseEntity<>(expectedExercise, HttpStatus.CONFLICT)).usingRecursiveComparison().isEqualTo(result);

    }


}