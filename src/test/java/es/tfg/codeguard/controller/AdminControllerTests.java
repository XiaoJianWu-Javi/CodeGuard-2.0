package es.tfg.codeguard.controller;

import es.tfg.codeguard.controller.imp.AdminControllerImp;
import es.tfg.codeguard.model.dto.*;
import es.tfg.codeguard.model.entity.exercise.Exercise;
import es.tfg.codeguard.service.AdminService;
import es.tfg.codeguard.util.ExerciceNotFoundException;
import es.tfg.codeguard.util.ExerciseNotFoundException;
import es.tfg.codeguard.util.PasswordNotValidException;
import es.tfg.codeguard.util.UserNotFoundException;
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

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class AdminControllerTests {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminControllerImp adminControllerImp;

    @ParameterizedTest
    @ValueSource(strings = {"FirstUser", "SecondUser", "ThirdUser", "FourthUser"})
    void deleteUserByIdTest(String username) {

        when(adminService.deleteUser(username)).thenReturn(new UserDTO(username, false, false, new java.util.ArrayList<>()));

        UserDTO esperado = adminService.deleteUser(username);

        ResponseEntity<UserDTO> resultado = adminControllerImp.deleteUser(username);

        assertThat(new ResponseEntity<>(esperado, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(resultado);

    }

    @ParameterizedTest
    @ValueSource(strings = {"FirstUser", "SecondUser", "ThirdUser", "FourthUser"})
    void deleteUserByIdTestUserNotFound(String username) {

        when(adminService.deleteUser(username)).thenThrow(UserNotFoundException.class);

        ResponseEntity<UserDTO> resultado = adminControllerImp.deleteUser(username);

        assertThat(new ResponseEntity<>(HttpStatus.NOT_FOUND)).usingRecursiveComparison().isEqualTo(resultado);

    }

    @Test
    void updatePasswordControllerTest() {

        AuthDTO authDTOGandalf = new AuthDTO("Gandalf", "newSecurePassword1234");
        UserPassDTO userPassDTOGandalf = new UserPassDTO("Gandalf", true);

        when(adminService.updatePassword(authDTOGandalf.username(), "newSecurePassword1234")).thenReturn(userPassDTOGandalf);

        UserPassDTO esperado = adminService.updatePassword(authDTOGandalf.username(), "newSecurePassword1234");
        ResponseEntity<UserPassDTO> resultado = adminControllerImp.updatePassword(authDTOGandalf);

        assertThat(new ResponseEntity<>(esperado, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(resultado);


        AuthDTO authDTOHoudini = new AuthDTO("Houdini", "myBestFlight9876");
        UserPassDTO userPassDTOHoudini = new UserPassDTO("Houdini", true);

        when(adminService.updatePassword(authDTOHoudini.username(), "myBestFlight9876")).thenReturn(userPassDTOHoudini);

        esperado = adminService.updatePassword(authDTOHoudini.username(), "myBestFlight9876");
        resultado = adminControllerImp.updatePassword(authDTOHoudini);

        assertThat(new ResponseEntity<>(esperado, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(resultado);


        AuthDTO authDTOMickeyMouse = new AuthDTO("MickeyMouse", "MickeyTheWizard87");
        UserPassDTO userPassDTOMickeyMouse = new UserPassDTO("MickeyMouse", true);

        when(adminService.updatePassword(authDTOMickeyMouse.username(), "MickeyTheWizard87")).thenReturn(userPassDTOMickeyMouse);

        esperado = adminService.updatePassword(authDTOMickeyMouse.username(), "MickeyTheWizard87");
        resultado = adminControllerImp.updatePassword(authDTOMickeyMouse);

        assertThat(new ResponseEntity<>(esperado, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(resultado);

    }

    @Test
    void updatePasswordControllerTestPasswordNotValid() {

        AuthDTO authDTOGandalf = new AuthDTO("Gandalf", "");
        UserPassDTO userPassDTOGandalf = new UserPassDTO("Gandalf", true);

        when(adminService.updatePassword(authDTOGandalf.username(), "")).thenThrow(PasswordNotValidException.class);

        ResponseEntity<UserPassDTO> resultado = adminControllerImp.updatePassword(authDTOGandalf);

        assertThat(new ResponseEntity<>(HttpStatus.BAD_REQUEST)).usingRecursiveComparison().isEqualTo(resultado);


        AuthDTO authDTOHoudini = new AuthDTO("Houdini", "");
        UserPassDTO userPassDTOHoudini = new UserPassDTO("Houdini", true);

        when(adminService.updatePassword(authDTOHoudini.username(), "")).thenThrow(PasswordNotValidException.class);

        resultado = adminControllerImp.updatePassword(authDTOHoudini);

        assertThat(new ResponseEntity<>(HttpStatus.BAD_REQUEST)).usingRecursiveComparison().isEqualTo(resultado);


        AuthDTO authDTOMickeyMouse = new AuthDTO("MickeyMouse", "MagicMagicianMickey234");
        UserPassDTO userPassDTOHMickeyMouse = new UserPassDTO("MickeyMouse", true);

        when(adminService.updatePassword(authDTOMickeyMouse.username(), "MagicMagicianMickey234")).thenThrow(UserNotFoundException.class);

        resultado = adminControllerImp.updatePassword(authDTOMickeyMouse);

        assertThat(new ResponseEntity<>(HttpStatus.NOT_FOUND)).usingRecursiveComparison().isEqualTo(resultado);


        AuthDTO authDTODrStrange = new AuthDTO("DrStrange", "newSecurePassword1234");
        UserPassDTO userPassDTODrStrange = new UserPassDTO("DrStrange", true);

        when(adminService.updatePassword(authDTODrStrange.username(), "newSecurePassword1234")).thenThrow(UserNotFoundException.class);

        resultado = adminControllerImp.updatePassword(authDTODrStrange);

        assertThat(new ResponseEntity<>(HttpStatus.NOT_FOUND)).usingRecursiveComparison().isEqualTo(resultado);

    }

    @Test
    void updateUserPrivilegesTest() {

        UserPrivilegesDTO userPrivilegesDTO1 = new UserPrivilegesDTO("theBestMagician", false, true);

        UserDTO expectedUser = new UserDTO(userPrivilegesDTO1.username(), userPrivilegesDTO1.tester(), userPrivilegesDTO1.creator(), List.of());

        when(adminService.updateUserPrivileges(userPrivilegesDTO1)).thenReturn(expectedUser);

        ResponseEntity<UserDTO> result = adminControllerImp.updateUserPrivileges(userPrivilegesDTO1);

        assertThat(new ResponseEntity<>(expectedUser, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(result);


        UserPrivilegesDTO userPrivilegesDTO2 = new UserPrivilegesDTO("DrStrange", false, true);

        expectedUser = new UserDTO(userPrivilegesDTO2.username(), userPrivilegesDTO2.tester(), userPrivilegesDTO2.creator(), List.of());

        when(adminService.updateUserPrivileges(userPrivilegesDTO2)).thenReturn(expectedUser);

        result = adminControllerImp.updateUserPrivileges(userPrivilegesDTO2);

        assertThat(new ResponseEntity<>(expectedUser, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(result);


        UserPrivilegesDTO userPrivilegesDTO3 = new UserPrivilegesDTO("Damian", false, true);

        expectedUser = new UserDTO(userPrivilegesDTO3.username(), userPrivilegesDTO3.tester(), userPrivilegesDTO3.creator(), List.of());

        when(adminService.updateUserPrivileges(userPrivilegesDTO3)).thenReturn(expectedUser);

        result = adminControllerImp.updateUserPrivileges(userPrivilegesDTO3);

        assertThat(new ResponseEntity<>(expectedUser, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(result);


        UserPrivilegesDTO userPrivilegesDTO4 = new UserPrivilegesDTO("Rachel", false, true);

        expectedUser = new UserDTO(userPrivilegesDTO4.username(), userPrivilegesDTO4.tester(), userPrivilegesDTO4.creator(), List.of());

        when(adminService.updateUserPrivileges(userPrivilegesDTO4)).thenReturn(expectedUser);

        result = adminControllerImp.updateUserPrivileges(userPrivilegesDTO4);

        assertThat(new ResponseEntity<>(expectedUser, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(result);

    }

    @ParameterizedTest
    @ValueSource(strings = {"43ero", "t3rreta", "+´`2marck", "º-º", ":-)ori"})
    void updateUserPrivilegeTestUserNotFound() {

        UserPrivilegesDTO userPrivilegesDTO1 = new UserPrivilegesDTO("theBestMagician", false, true);

        when(adminService.updateUserPrivileges(userPrivilegesDTO1)).thenThrow(UserNotFoundException.class);

        ResponseEntity<UserDTO> result = adminControllerImp.updateUserPrivileges(userPrivilegesDTO1);

        assertThat(new ResponseEntity<>(HttpStatus.NOT_FOUND)).usingRecursiveComparison().isEqualTo(result);

    }

    @Test
    void adminUpdateTestForExerciseTest(){
        Exercise exercise = new Exercise();
        exercise.setId("project-euler-9");
        exercise.setTitle("Project Euler 9");
        exercise.setDescription("description1");
        exercise.setTest("test");
        exercise.setCreator("creator1");
        exercise.setSolutions(Map.of());

        String newTest = "newTest";

        Exercise expectedExercise = new Exercise();
        expectedExercise.setId("project-euler-9");
        expectedExercise.setTitle("Project Euler 9");
        expectedExercise.setDescription("description1");
        expectedExercise.setTest(newTest);
        expectedExercise.setCreator("creator1");
        expectedExercise.setSolutions(Map.of());

        ExerciseDTO expectedExerciseDTO = new ExerciseDTO(expectedExercise);

        when(adminService.updateTestForExercise(exercise.getId(), "newTest")).thenReturn(expectedExerciseDTO);

        ResponseEntity<ExerciseDTO> response = adminControllerImp.updateTestForExercise(exercise.getId(), newTest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().equals(expectedExerciseDTO));
    }

    @ParameterizedTest
    @ValueSource(strings = {"43ero", "t3rreta", "+´`2marck", "º-º", ":-)ori"})
    void adminUpdateTestForExerciseTestNotFound(String exerciseId){
        when(adminService.updateTestForExercise(exerciseId, "newTest"))
                .thenThrow(new ExerciseNotFoundException("Exercise not found [ " + exerciseId + " ]"));

        ResponseEntity<ExerciseDTO> response = adminControllerImp.updateTestForExercise(exerciseId, "newTest");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void adminDeleteTestFromExerciseTest(){
        Exercise exercise = new Exercise();
        exercise.setId("project-euler-9");
        exercise.setTitle("Project Euler 9");
        exercise.setDescription("description1");
        exercise.setTest("test");
        exercise.setCreator("creator1");
        exercise.setSolutions(Map.of());

        Exercise expectedExercise = new Exercise();
        expectedExercise.setId("project-euler-9");
        expectedExercise.setTitle("Project Euler 9");
        expectedExercise.setDescription("description1");
        expectedExercise.setTest(null);
        expectedExercise.setCreator("creator1");
        expectedExercise.setSolutions(Map.of());

        ExerciseDTO expectedExerciseDTO = new ExerciseDTO(expectedExercise);

        when(adminService.deleteTestFromExercise(exercise.getId())).thenReturn(expectedExerciseDTO);

        ResponseEntity<ExerciseDTO> response = adminControllerImp.deleteTestFromExercise(exercise.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().equals(expectedExerciseDTO));
    }

    @ParameterizedTest
    @ValueSource(strings = {"43ero", "t3rreta", "+´`2marck", "º-º", ":-)ori"})
    void adminDeleteTestFromExerciseTestNotFound(String exerciseId){
        when(adminService.deleteTestFromExercise(exerciseId))
                .thenThrow(new ExerciseNotFoundException("Exercise not found [ " + exerciseId + " ]"));

        ResponseEntity<ExerciseDTO> response = adminControllerImp.deleteTestFromExercise(exerciseId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void adminDeleteExerciseTest(){
        Exercise exercise = new Exercise();
        exercise.setId("project-euler-9");
        exercise.setTitle("Project Euler 9");
        exercise.setDescription("description1");
        exercise.setTest("test");
        exercise.setCreator("creator1");
        exercise.setSolutions(Map.of());

        ExerciseDTO expectedExerciseDTO = new ExerciseDTO(exercise);

        when(adminService.deleteExercise(exercise.getId())).thenReturn(expectedExerciseDTO);

        ResponseEntity<ExerciseDTO> response = adminControllerImp.deleteExercise(exercise.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().equals(expectedExerciseDTO));
    }

    @ParameterizedTest
    @ValueSource(strings = {"43ero", "t3rreta", "+´`2marck", "º-º", ":-)ori"})
    void adminDeleteExerciseTestNotFound(String exerciseId){
        when(adminService.deleteExercise(exerciseId))
                .thenThrow(new ExerciseNotFoundException("Exercise not found [ " + exerciseId + " ]"));

        ResponseEntity<ExerciseDTO> response = adminControllerImp.deleteExercise(exerciseId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }
}