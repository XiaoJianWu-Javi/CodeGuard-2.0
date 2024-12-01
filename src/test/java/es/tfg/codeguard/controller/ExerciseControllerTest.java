package es.tfg.codeguard.controller;

import es.tfg.codeguard.controller.imp.ExerciseControllerImp;
import es.tfg.codeguard.model.dto.CreateExerciseDTO;
import es.tfg.codeguard.model.dto.ExerciseDTO;
import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.service.AdminService;
import es.tfg.codeguard.service.ExerciseService;
import es.tfg.codeguard.util.*;
import org.junit.jupiter.api.Assertions;
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

        when(exerciseService.getExerciseById(exerciseId)).thenThrow(ExerciseNotFoundException.class);

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
        when(exerciseService.getAllExercisesPaginated("Je", 1, false)).thenReturn(new ArrayList<ExerciseDTO>(Arrays.asList()));

        List<ExerciseDTO> resultado = exerciseService.getAllExercisesPaginated("Je", 1, false);
        List<ExerciseDTO> expected = exerciseService.getAllExercisesPaginated("Je", 1, false);

        ResponseEntity<List<ExerciseDTO>> esperado = exerciseControllerImp.getAllExercisesPaginated("Je", 1, false);
        ResponseEntity<List<ExerciseDTO>> result = exerciseControllerImp.getAllExercisesPaginated("Je", 1, false);

        Assertions.assertEquals(0, result.getBody().size());
        assertThat(new ResponseEntity<>(expected, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(result);


        when(exerciseService.getAllExercisesPaginated("je", 1, false)).thenReturn(new ArrayList<ExerciseDTO>(Arrays.asList(exercise1)));

        resultado = exerciseService.getAllExercisesPaginated("je", 1, false);
        expected = exerciseService.getAllExercisesPaginated("je", 1, false);

        esperado = exerciseControllerImp.getAllExercisesPaginated("je", 1, false);
        result = exerciseControllerImp.getAllExercisesPaginated("je", 1, false);

        Assertions.assertEquals(1, result.getBody().size());
        assertThat(new ResponseEntity<>(expected, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(result);


        when(exerciseService.getAllExercisesPaginated("g", 0, false)).thenReturn(new ArrayList<ExerciseDTO>(Arrays.asList(exercise2, exercise3)));

        resultado = exerciseService.getAllExercisesPaginated("g", 0, false);
        expected = exerciseService.getAllExercisesPaginated("g", 0, false);

        esperado = exerciseControllerImp.getAllExercisesPaginated("g", 0, false);
        result = exerciseControllerImp.getAllExercisesPaginated("g", 0, false);

        Assertions.assertEquals(2, result.getBody().size());
        assertThat(new ResponseEntity<>(expected, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(result);


        when(exerciseService.getAllExercisesPaginated("g", 0, true)).thenReturn(new ArrayList<ExerciseDTO>(Arrays.asList(exercise3, exercise2)));

        resultado = exerciseService.getAllExercisesPaginated("g", 0, true);
        expected = exerciseService.getAllExercisesPaginated("g", 0, true);

        esperado = exerciseControllerImp.getAllExercisesPaginated("g", 0, true);
        result = exerciseControllerImp.getAllExercisesPaginated("g", 0, true);

        Assertions.assertEquals(2, result.getBody().size());
        assertThat(new ResponseEntity<>(expected, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(result);

    }

    @ParameterizedTest
    @CsvSource({
            "Houdini,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJIb3VkaW5pIiwiaWF0IjoxNzMyNzc3OTkzLCJleHAiOjE3MzI4NjQzOTN9.BIOCXkd2KpiwBbaiv1lOt-gs5oDFQMF7dmei7MuWw4w,Magic Music Box,\"The Magic Music Box exercise involves simulating a music box\",magic-music-box",
            "Rachel,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJSYWNoZWwiLCJpYXQiOjE3MzI3Nzg1NTEsImV4cCI6MTczMjg2NDk1MX0.Q-hQgnLCHAeLiG2mAjnaXb5ftQLfmis75jbvbLBFMlA,Build Square,\"I will give you an integer. Give me back a shape that is as long and wide as the integer\",build-square",
            "Blaise,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJCbGFpc2UiLCJpYXQiOjE3MzI3Nzg5OTgsImV4cCI6MTczMjg2NTM5OH0.B2ueU8_beww-gYbobXn2aqdcDvDdxb6xcIsEwuSyEFw,Delta Bits,\"Complete the function to determine the number of bits required to convert integer A to integer B (where A and B >= 0)\",delta-bits",
            "Albus,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJBbGJ1cyIsImlhdCI6MTczMjc3OTE3NywiZXhwIjoxNzMyODY1NTc3fQ.9jTa2oHY9-KQlwXzBIIydCaIkimjDBZzomGIPlryYqk,Reverse words,\"Complete the function that accepts a string parameter, and reverses each word in the string\",reverse-words"
    })
    void createExerciseTest(String username, String userToken, String exerciseTitle, String exerciseDescription, String exerciseId) {
        CreateExerciseDTO createExerciseDTO = new CreateExerciseDTO(exerciseTitle, exerciseDescription);
        when(exerciseService.createExercise(userToken, createExerciseDTO)).thenReturn(new ExerciseDTO(exerciseId, exerciseTitle, exerciseDescription, "", username));

        ExerciseDTO expectedExercise = exerciseService.createExercise(userToken, createExerciseDTO);

        ResponseEntity<ExerciseDTO> result = exerciseControllerImp.createExercise(userToken, createExerciseDTO);

        assertThat(new ResponseEntity<>(expectedExercise, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(result);

    }

    @ParameterizedTest
    @CsvSource({
            "Houdini,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJIb3VkaW5pIiwiaWF0IjoxNzMyNzc3OTkzLCJleHAiOjE3MzI4NjQzOTN9.BIOCXkd2KpiwBbaiv1lOt-gs5oDFQMF7dmei7MuWw4w,,\"The Magic Music Box exercise involves simulating a music box\",project-euler-9",
            "Rachel,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJSYWNoZWwiLCJpYXQiOjE3MzI3Nzg1NTEsImV4cCI6MTczMjg2NDk1MX0.Q-hQgnLCHAeLiG2mAjnaXb5ftQLfmis75jbvbLBFMlA,,\"I will give you an integer. Give me back a shape that is as long and wide as the integer\",project-euler-2",
            "Blaise,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJCbGFpc2UiLCJpYXQiOjE3MzI3Nzg5OTgsImV4cCI6MTczMjg2NTM5OH0.B2ueU8_beww-gYbobXn2aqdcDvDdxb6xcIsEwuSyEFw,,\"Write a function that takes a string of braces, and determines if the order of the braces is valid\",valid-braces",
            "Albus,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJBbGJ1cyIsImlhdCI6MTczMjc3OTE3NywiZXhwIjoxNzMyODY1NTc3fQ.9jTa2oHY9-KQlwXzBIIydCaIkimjDBZzomGIPlryYqk,,\"We need a simple function that determines if a plural is needed or not\",plural"
    })
    void createExerciseFailTest(String username, String userToken, String exerciseTitle, String exerciseDescription, String exerciseId) {
        CreateExerciseDTO createExerciseDTO = new CreateExerciseDTO(exerciseTitle, exerciseDescription);
        when(exerciseService.createExercise(userToken, createExerciseDTO)).thenThrow(ExerciseTitleNotValidException.class);

        ResponseEntity<ExerciseDTO> result = exerciseControllerImp.createExercise(userToken, createExerciseDTO);

        assertThat(new ResponseEntity<>(HttpStatus.BAD_REQUEST)).usingRecursiveComparison().isEqualTo(result);

    }

    @ParameterizedTest
    @CsvSource({
            "Houdini,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJIb3VkaW5pIiwiaWF0IjoxNzMyNzc3OTkzLCJleHAiOjE3MzI4NjQzOTN9.BIOCXkd2KpiwBbaiv1lOt-gs5oDFQMF7dmei7MuWw4w,Magic Music Box,,magic-music-box",
            "Rachel,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJSYWNoZWwiLCJpYXQiOjE3MzI3Nzg1NTEsImV4cCI6MTczMjg2NDk1MX0.Q-hQgnLCHAeLiG2mAjnaXb5ftQLfmis75jbvbLBFMlA,Build Square,,build-square",
            "Blaise,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJCbGFpc2UiLCJpYXQiOjE3MzI3Nzg5OTgsImV4cCI6MTczMjg2NTM5OH0.B2ueU8_beww-gYbobXn2aqdcDvDdxb6xcIsEwuSyEFw,Delta Bits,,delta-bits",
            "Albus,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJBbGJ1cyIsImlhdCI6MTczMjc3OTE3NywiZXhwIjoxNzMyODY1NTc3fQ.9jTa2oHY9-KQlwXzBIIydCaIkimjDBZzomGIPlryYqk,Reverse words,,reverse-words"
    })
    void createExerciseTestDescriptionNotValid(String username, String userToken, String exerciseTitle, String exerciseDescription, String exerciseId) {
        CreateExerciseDTO createExerciseDTO = new CreateExerciseDTO(exerciseTitle, exerciseDescription);
        when(exerciseService.createExercise(userToken, createExerciseDTO)).thenThrow(ExerciseDescriptionNotValid.class);

        ResponseEntity<ExerciseDTO> result = exerciseControllerImp.createExercise(userToken, createExerciseDTO);

        assertThat(new ResponseEntity<>(HttpStatus.BAD_REQUEST)).usingRecursiveComparison().isEqualTo(result);

    }

    @ParameterizedTest
    @CsvSource({
            "Houdini,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJIb3VkaW5pIiwiaWF0IjoxNzMyNzc3OTkzLCJleHAiOjE3MzI4NjQzOTN9.BIOCXkd2KpiwBbaiv1lOt-gs5oDFQMF7dmei7MuWw4w,,\"The Magic Music Box exercise involves simulating a music box\",project-euler-9",
            "Rachel,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJSYWNoZWwiLCJpYXQiOjE3MzI3Nzg1NTEsImV4cCI6MTczMjg2NDk1MX0.Q-hQgnLCHAeLiG2mAjnaXb5ftQLfmis75jbvbLBFMlA,,\"I will give you an integer. Give me back a shape that is as long and wide as the integer\",project-euler-2",
            "Blaise,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJCbGFpc2UiLCJpYXQiOjE3MzI3Nzg5OTgsImV4cCI6MTczMjg2NTM5OH0.B2ueU8_beww-gYbobXn2aqdcDvDdxb6xcIsEwuSyEFw,,\"Write a function that takes a string of braces, and determines if the order of the braces is valid\",valid-braces",
            "Albus,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJBbGJ1cyIsImlhdCI6MTczMjc3OTE3NywiZXhwIjoxNzMyODY1NTc3fQ.9jTa2oHY9-KQlwXzBIIydCaIkimjDBZzomGIPlryYqk,,\"We need a simple function that determines if a plural is needed or not\",plural"
    })
    void CreateExerciseTestExerciseAlreadyExist(String username, String userToken, String exerciseTitle, String exerciseDescription, String exerciseId) {
        CreateExerciseDTO createExerciseDTO = new CreateExerciseDTO(exerciseTitle, exerciseDescription);
        when(exerciseService.createExercise(userToken, createExerciseDTO)).thenThrow(ExerciseAlreadyExistException.class);

        ResponseEntity<ExerciseDTO> result = exerciseControllerImp.createExercise(userToken, createExerciseDTO);

        assertThat(new ResponseEntity<>(HttpStatus.CONFLICT)).usingRecursiveComparison().isEqualTo(result);

    }

    @ParameterizedTest
    @CsvSource({
            "Houdini,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJIb3VkaW5pIiwiaWF0IjoxNzMyNzc3OTkzLCJleHAiOjE3MzI4NjQzOTN9.BIOCXkd2KpiwBbaiv1lOt-gs5oDFQMF7dmei7MuWw4w,,\"The Magic Music Box exercise involves simulating a music box\",project-euler-9",
            "Rachel,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJSYWNoZWwiLCJpYXQiOjE3MzI3Nzg1NTEsImV4cCI6MTczMjg2NDk1MX0.Q-hQgnLCHAeLiG2mAjnaXb5ftQLfmis75jbvbLBFMlA,,\"I will give you an integer. Give me back a shape that is as long and wide as the integer\",project-euler-2",
            "Blaise,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJCbGFpc2UiLCJpYXQiOjE3MzI3Nzg5OTgsImV4cCI6MTczMjg2NTM5OH0.B2ueU8_beww-gYbobXn2aqdcDvDdxb6xcIsEwuSyEFw,,\"Write a function that takes a string of braces, and determines if the order of the braces is valid\",valid-braces",
            "Albus,eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJBbGJ1cyIsImlhdCI6MTczMjc3OTE3NywiZXhwIjoxNzMyODY1NTc3fQ.9jTa2oHY9-KQlwXzBIIydCaIkimjDBZzomGIPlryYqk,,\"We need a simple function that determines if a plural is needed or not\",plural"
    })
    void createExerciseTestNotAllowedUser(String username, String userToken, String exerciseTitle, String exerciseDescription, String exerciseId) {
        CreateExerciseDTO createExerciseDTO = new CreateExerciseDTO(exerciseTitle, exerciseDescription);
        when(exerciseService.createExercise(userToken, createExerciseDTO)).thenThrow(NotAllowedUserException.class);

        ResponseEntity<ExerciseDTO> result = exerciseControllerImp.createExercise(userToken, createExerciseDTO);

        assertThat(new ResponseEntity<>(HttpStatus.FORBIDDEN)).usingRecursiveComparison().isEqualTo(result);

    }

}