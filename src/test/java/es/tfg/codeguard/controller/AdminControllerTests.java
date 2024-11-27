package es.tfg.codeguard.controller;

import es.tfg.codeguard.controller.imp.AdminControllerImp;
import es.tfg.codeguard.model.dto.AuthDTO;
import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.service.AdminService;
import es.tfg.codeguard.util.PasswordNotValidException;
import es.tfg.codeguard.util.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

        UserDTO userDTO = new UserDTO(username, false, false, new java.util.ArrayList<>());

        when(adminService.deleteUser(username)).thenReturn(userDTO);

        UserDTO esperado = adminService.deleteUser(username);

        ResponseEntity<UserDTO> resultado = adminControllerImp.deleteUser(username);

        assertThat(new ResponseEntity<>(esperado, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(resultado);

    }

    @ParameterizedTest
    @ValueSource(strings = {"FirstUser", "SecondUser", "ThirdUser", "FourthUser"})
    void InvalidDeleteUserByIdTest(String username) {

        when(adminService.deleteUser(username)).thenThrow(new UserNotFoundException("User not found [" + username + "]"));

        ResponseEntity<UserDTO> resultado = adminControllerImp.deleteUser(username);

        assertThat(new ResponseEntity<>(HttpStatus.NOT_FOUND)).usingRecursiveComparison().isEqualTo(resultado);

    }


    //TODO: UPDATE TESTER

    @Test
    void UpdatePasswordControllerTest(){

        AuthDTO authDTOGandalf = new AuthDTO("Gandalf", "newSecurePassword1234");
        UserPassDTO userPassDTOGandalf = new UserPassDTO("Gandalf",true);

        when(adminService.updateUser(authDTOGandalf.username(), "newSecurePassword1234")).thenReturn(userPassDTOGandalf);

        UserPassDTO esperado = adminService.updateUser(authDTOGandalf.username(), "newSecurePassword1234");
        ResponseEntity<UserPassDTO> resultado = adminControllerImp.updateUser(authDTOGandalf);

        assertThat(new ResponseEntity<>(esperado, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(resultado);


        AuthDTO authDTOHoudini = new AuthDTO("Houdini", "myBestFlight9876");
        UserPassDTO userPassDTOHoudini = new UserPassDTO("Houdini",true);

        when(adminService.updateUser(authDTOHoudini.username(), "myBestFlight9876")).thenReturn(userPassDTOHoudini);

        esperado = adminService.updateUser(authDTOHoudini.username(), "myBestFlight9876");
        resultado = adminControllerImp.updateUser(authDTOHoudini);

        assertThat(new ResponseEntity<>(esperado, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(resultado);


        AuthDTO authDTOMickeyMouse = new AuthDTO("MickeyMouse", "myBestFlight9876");
        UserPassDTO userPassDTOMickeyMouse = new UserPassDTO("MickeyMouse",true);

        when(adminService.updateUser(authDTOMickeyMouse.username(), "myBestFlight9876")).thenReturn(userPassDTOMickeyMouse);

        esperado = adminService.updateUser(authDTOMickeyMouse.username(), "myBestFlight9876");
        resultado = adminControllerImp.updateUser(authDTOMickeyMouse);

        assertThat(new ResponseEntity<>(esperado, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(resultado);

    }

    @Test
    void FailedUpdatePasswordControllerTest(){

        AuthDTO authDTOGandalf = new AuthDTO("Gandalf", "");
        UserPassDTO userPassDTOGandalf = new UserPassDTO("Gandalf",true);

        when(adminService.updateUser(authDTOGandalf.username(), "")).thenThrow(PasswordNotValidException.class);

        ResponseEntity<UserPassDTO> resultado = adminControllerImp.updateUser(authDTOGandalf);

        assertThat(new ResponseEntity<>(HttpStatus.BAD_REQUEST)).usingRecursiveComparison().isEqualTo(resultado);


        AuthDTO authDTOHoudini = new AuthDTO("Houdini", "");
        UserPassDTO userPassDTOHoudini = new UserPassDTO("Houdini",true);

        when(adminService.updateUser(authDTOHoudini.username(), "")).thenThrow(PasswordNotValidException.class);

        resultado = adminControllerImp.updateUser(authDTOHoudini);

        assertThat(new ResponseEntity<>(HttpStatus.BAD_REQUEST)).usingRecursiveComparison().isEqualTo(resultado);


        AuthDTO authDTOMickeyMouse = new AuthDTO("MickeyMouse", "MagicMagicianMickey234");
        UserPassDTO userPassDTOHMickeyMouse = new UserPassDTO("MickeyMouse",true);

        when(adminService.updateUser(authDTOMickeyMouse.username(), "MagicMagicianMickey234")).thenThrow(UserNotFoundException.class);

        resultado = adminControllerImp.updateUser(authDTOMickeyMouse);

        assertThat(new ResponseEntity<>(HttpStatus.NOT_FOUND)).usingRecursiveComparison().isEqualTo(resultado);


        AuthDTO authDTODrStrange = new AuthDTO("DrStrange", "newSecurePassword1234");
        UserPassDTO userPassDTODrStrange = new UserPassDTO("DrStrange",true);

        when(adminService.updateUser(authDTODrStrange.username(), "newSecurePassword1234")).thenThrow(UserNotFoundException.class);

        resultado = adminControllerImp.updateUser(authDTODrStrange);

        assertThat(new ResponseEntity<>(HttpStatus.NOT_FOUND)).usingRecursiveComparison().isEqualTo(resultado);

    }

    @ParameterizedTest
    @ValueSource(strings = {"FirstUser", "SecondUser", "Damian", "Rachel"})
    void GrantTesterControllerTest(String username){

        UserDTO userDTO = new UserDTO(username, false, false, new java.util.ArrayList<>());

        when(adminService.grantTester(username)).thenReturn(userDTO);

        UserDTO esperado = adminService.grantTester(username);

        ResponseEntity<UserDTO> resultado = adminControllerImp.grantTester(username);

        assertThat(new ResponseEntity<>(esperado, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(resultado);

    }

    @ParameterizedTest
    @ValueSource(strings = {"FirstUser", "SecondUser", "Damian", "Rachel"})
    void FailedGrantTesterControllerTest(String username){

        UserDTO userDTO = new UserDTO(username, false, false, new java.util.ArrayList<>());

        when(adminService.grantTester(username)).thenThrow(UserNotFoundException.class);

        ResponseEntity<UserDTO> resultado = adminControllerImp.grantTester(username);

        assertThat(new ResponseEntity<>(HttpStatus.NOT_FOUND)).usingRecursiveComparison().isEqualTo(resultado);

    }

    @ParameterizedTest
    @ValueSource(strings = {"FirstUser", "SecondUser", "Damian", "Rachel"})
    void GrantCreatorControllerTest(String username){

        UserDTO userDTO = new UserDTO(username, false, false, new java.util.ArrayList<>());

        when(adminService.grantCreator(username)).thenReturn(userDTO);

        UserDTO esperado = adminService.grantCreator(username);

        ResponseEntity<UserDTO> resultado = adminControllerImp.grantCreator(username);

        assertThat(new ResponseEntity<>(esperado, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(resultado);

    }

    @ParameterizedTest
    @ValueSource(strings = {"FirstUser", "SecondUser", "Damian", "Rachel"})
    void FailedGrantCreatorControllerTest(String username){

        UserDTO userDTO = new UserDTO(username, false, false, new java.util.ArrayList<>());

        when(adminService.grantCreator(username)).thenThrow(UserNotFoundException.class);

        ResponseEntity<UserDTO> resultado = adminControllerImp.grantCreator(username);

        assertThat(new ResponseEntity<>(HttpStatus.NOT_FOUND)).usingRecursiveComparison().isEqualTo(resultado);

    }

    @ParameterizedTest
    @ValueSource(strings = {"FirstUser", "SecondUser", "Damian", "Rachel"})
    void RevokeTesterControllerTest(String username){

        UserDTO userDTO = new UserDTO(username, false, false, new java.util.ArrayList<>());

        when(adminService.revokeTester(username)).thenReturn(userDTO);

        UserDTO esperado = adminService.revokeTester(username);

        ResponseEntity<UserDTO> resultado = adminControllerImp.revokeTester(username);

        assertThat(new ResponseEntity<>(esperado, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(resultado);

    }

    @ParameterizedTest
    @ValueSource(strings = {"FirstUser", "SecondUser", "Damian", "Rachel"})
    void FailedRevokeTesterControllerTest(String username){

        UserDTO userDTO = new UserDTO(username, false, false, new java.util.ArrayList<>());

        when(adminService.revokeTester(username)).thenThrow(UserNotFoundException.class);

        ResponseEntity<UserDTO> resultado = adminControllerImp.revokeTester(username);

        assertThat(new ResponseEntity<>(HttpStatus.NOT_FOUND)).usingRecursiveComparison().isEqualTo(resultado);

    }

    @ParameterizedTest
    @ValueSource(strings = {"FirstUser", "SecondUser", "Damian", "Rachel"})
    void RevokeCreatorControllerTest(String username){

        UserDTO userDTO = new UserDTO(username, false, false, new java.util.ArrayList<>());

        when(adminService.revokeCreator(username)).thenReturn(userDTO);

        UserDTO esperado = adminService.revokeCreator(username);

        ResponseEntity<UserDTO> resultado = adminControllerImp.revokeCreator(username);

        assertThat(new ResponseEntity<>(esperado, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(resultado);

    }

    @ParameterizedTest
    @ValueSource(strings = {"FirstUser", "SecondUser", "Damian", "Rachel"})
    void FailedRevokeCreatorControllerTest(String username){

        UserDTO userDTO = new UserDTO(username, false, false, new java.util.ArrayList<>());

        when(adminService.revokeCreator(username)).thenThrow(UserNotFoundException.class);

        ResponseEntity<UserDTO> resultado = adminControllerImp.revokeCreator(username);

        assertThat(new ResponseEntity<>(HttpStatus.NOT_FOUND)).usingRecursiveComparison().isEqualTo(resultado);

    }

    @ParameterizedTest
    @ValueSource(strings = {"FirstUser", "SecondUser", "Damian", "Rachel"})
    void GrantAllPrivilegesControllerTest(String username){

        UserDTO userDTO = new UserDTO(username, false, false, new java.util.ArrayList<>());

        when(adminService.grantAllPrivileges(username)).thenReturn(userDTO);

        UserDTO esperado = adminService.grantAllPrivileges(username);

        ResponseEntity<UserDTO> resultado = adminControllerImp.grantAllPrivileges(username);

        assertThat(new ResponseEntity<>(esperado, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(resultado);

    }

    @ParameterizedTest
    @ValueSource(strings = {"FirstUser", "SecondUser", "Damian", "Rachel"})
    void FailedGrantAllPrivilegesControllerTest(String username){

        UserDTO userDTO = new UserDTO(username, false, false, new java.util.ArrayList<>());

        when(adminService.grantAllPrivileges(username)).thenThrow(UserNotFoundException.class);

        ResponseEntity<UserDTO> resultado = adminControllerImp.grantAllPrivileges(username);

        assertThat(new ResponseEntity<>(HttpStatus.NOT_FOUND)).usingRecursiveComparison().isEqualTo(resultado);

    }

    @ParameterizedTest
    @ValueSource(strings = {"FirstUser", "SecondUser", "Damian", "Rachel"})
    void RevokeAllPrivilegesControllerTest(String username){

        UserDTO userDTO = new UserDTO(username, false, false, new java.util.ArrayList<>());

        when(adminService.revokeAllPrivileges(username)).thenReturn(userDTO);

        UserDTO esperado = adminService.revokeAllPrivileges(username);

        ResponseEntity<UserDTO> resultado = adminControllerImp.revokeAllPrivileges(username);

        assertThat(new ResponseEntity<>(esperado, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(resultado);

    }

    @ParameterizedTest
    @ValueSource(strings = {"FirstUser", "SecondUser", "Damian", "Rachel"})
    void FailedRevokeAllPrivilegesControllerTest(String username){

        UserDTO userDTO = new UserDTO(username, false, false, new java.util.ArrayList<>());

        when(adminService.revokeAllPrivileges(username)).thenThrow(UserNotFoundException.class);

        ResponseEntity<UserDTO> resultado = adminControllerImp.revokeAllPrivileges(username);

        assertThat(new ResponseEntity<>(HttpStatus.NOT_FOUND)).usingRecursiveComparison().isEqualTo(resultado);

    }


}
