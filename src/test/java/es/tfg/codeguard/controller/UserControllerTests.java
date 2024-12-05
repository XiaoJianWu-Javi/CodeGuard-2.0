package es.tfg.codeguard.controller;

import es.tfg.codeguard.controller.imp.UserControllerImp;
import es.tfg.codeguard.model.dto.ChangePasswordDTO;
import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.model.dto.UserRestoreDTO;
import es.tfg.codeguard.model.repository.user.UserRepository;
import es.tfg.codeguard.service.AdminService;
import es.tfg.codeguard.service.JWTService;
import es.tfg.codeguard.service.UserService;
import es.tfg.codeguard.util.IncorrectPasswordException;
import es.tfg.codeguard.util.PasswordNotValidException;
import es.tfg.codeguard.util.UserNotFoundException;
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

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserControllerTests {

    @Mock
    private UserService userService;

    @Mock
    private AdminService adminService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserControllerImp userControllerImp;

    @Mock
    private JWTService jwtService;

    private UserDTO userDTO;
    private UserRestoreDTO restoreDTO;
    private UserPassDTO userPassDTO;

    @ParameterizedTest
    @ValueSource(strings = {"FirstUser", "SecondUser", "ThirdUser", "FourthUser"})
    void deleteUserTest(String username) {

        userDTO = new UserDTO(username, false, false, new ArrayList<>());

        when(userService.deleteUser(username)).thenReturn(userDTO);

        UserDTO expected = userService.deleteUser(username);

        ResponseEntity<UserDTO> result = userControllerImp.deleteUser(username);

        assertThat(new ResponseEntity<>(expected, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(result);

    }

    @ParameterizedTest
    @ValueSource(strings = {"FirstUser", "SecondUser", "ThirdUser", "FourthUser"})
    void deleteUserTestUserNotFound(String username) {

        when(userService.deleteUser(username)).thenThrow(UserNotFoundException.class);

        ResponseEntity<UserDTO> result = userControllerImp.deleteUser(username);

        assertThat(new ResponseEntity<>(HttpStatus.NOT_FOUND)).usingRecursiveComparison().isEqualTo(result);

    }
    
    @ParameterizedTest
    @ValueSource(strings = {"FirstUser", "SecondUser", "ThirdUser", "FourthUser"})
    void restoreUserTest(String username) {

    	restoreDTO= new UserRestoreDTO(username, "password");

        when(userService.restoreUser(restoreDTO.userName(), restoreDTO.password())).thenReturn(userDTO);

        UserDTO expected = userService.restoreUser(restoreDTO.userName(), restoreDTO.password());

        ResponseEntity<UserDTO> result = userControllerImp.restoreUser(restoreDTO);

        assertThat(new ResponseEntity<>(expected, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(result);

    }

    @ParameterizedTest
    @ValueSource(strings = {"FirstUser", "SecondUser", "ThirdUser", "FourthUser"})
    void restoreUserTestUserNotFound(String username) {

    	restoreDTO= new UserRestoreDTO(username, "password");

        when(userService.restoreUser(restoreDTO.userName(), restoreDTO.password())).thenThrow(UserNotFoundException.class);

        ResponseEntity<UserDTO> result = userControllerImp.restoreUser(restoreDTO);

        assertThat(new ResponseEntity<>(HttpStatus.NOT_FOUND)).usingRecursiveComparison().isEqualTo(result);

    }
    
    @ParameterizedTest
    @ValueSource(strings = {"FirstUser", "SecondUser", "ThirdUser", "FourthUser"})
    void restoreUserTestUserInvalidPassword(String username) {

    	restoreDTO= new UserRestoreDTO(username, "password");

        when(userService.restoreUser(restoreDTO.userName(), restoreDTO.password())).thenThrow(IncorrectPasswordException.class);

        ResponseEntity<UserDTO> result = userControllerImp.restoreUser(restoreDTO);

        assertThat(new ResponseEntity<>(HttpStatus.BAD_REQUEST)).usingRecursiveComparison().isEqualTo(result);

    }

    @ParameterizedTest
    @ValueSource(strings = {"aaron_pp", "saul_af", "dani_ro", "diego_ra"})
    void getUserByIdTest(String username) {

        userDTO = new UserDTO(username, false, false, new ArrayList<>());

        when(userService.getUserById(username)).thenReturn(userDTO);

        UserDTO expected = userService.getUserById(username);

        ResponseEntity<UserDTO> result = userControllerImp.getUserById(username);

        assertThat(new ResponseEntity<>(expected, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(result);

    }

    @ParameterizedTest
    @ValueSource(strings = {"huwhqñ", "duhqñidqidiq", "qi@dqiqdqdmiqdnqijdqjq", "#iuqdqdq", "uhy@", "_adam", "ad_am", "adam_#", "udhwqduqid???", "?¿ñ", "ññññññ", "adam#¿?!¡"})
    void getUserByIdTestUserNotFound(String username) {

        when(userService.getUserById(username)).thenThrow(UserNotFoundException.class);

        ResponseEntity<UserDTO> expected = userControllerImp.getUserById(username);

        assertThat(expected).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @Test
    void getAllUsersTest() {

        UserDTO user1 = new UserDTO("user1", false, false, new ArrayList<>());
        UserDTO user2 = new UserDTO("user2", false, false, new ArrayList<>());
        UserDTO user3 = new UserDTO("user3", false, false, new ArrayList<>());


        when(userService.getAllUsers()).thenReturn(new ArrayList<UserDTO>(Arrays.asList()));

        List<UserDTO> result = userService.getAllUsers();

        ResponseEntity<List<UserDTO>> expected = userControllerImp.getAllUser();

        Assertions.assertEquals(0, result.size());
        assertThat(expected).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(result, HttpStatus.OK));


        when(userService.getAllUsers()).thenReturn(new ArrayList<UserDTO>(Arrays.asList(user1)));

        result = userService.getAllUsers();

        expected = userControllerImp.getAllUser();

        Assertions.assertEquals(1, result.size());
        assertThat(expected).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(result, HttpStatus.OK));


        when(userService.getAllUsers()).thenReturn(new ArrayList<UserDTO>(Arrays.asList(user1, user2)));

        result = userService.getAllUsers();

        expected = userControllerImp.getAllUser();

        Assertions.assertEquals(2, result.size());
        assertThat(expected).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(result, HttpStatus.OK));


        when(userService.getAllUsers()).thenReturn(new ArrayList<UserDTO>(Arrays.asList(user1, user2, user3)));

        result = userService.getAllUsers();

        expected = userControllerImp.getAllUser();

        Assertions.assertEquals(3, result.size());
        assertThat(expected).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(result, HttpStatus.OK));

    }

    @Test
    void changeUserPasswordTest() {

        when(userService.changePassword(jwtService.createJwt(new UserPassDTO("Damian", false)), new ChangePasswordDTO("1234", "newSecurePassword1234"))).thenReturn(new UserDTO("Damian", false, false, List.of()));

        UserDTO expectedUser = userService.changePassword(jwtService.createJwt(new UserPassDTO("Damian", false)), new ChangePasswordDTO("1234", "newSecurePassword1234"));

        ResponseEntity<UserDTO> result = userControllerImp.changePassword(jwtService.createJwt(new UserPassDTO("Damian", false)), new ChangePasswordDTO("1234", "newSecurePassword1234"));

        assertThat(new ResponseEntity<>(expectedUser, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(result);


        when(userService.changePassword(jwtService.createJwt(new UserPassDTO("Rachel", false)), new ChangePasswordDTO("9876", "02_04_1999"))).thenReturn(new UserDTO("Rachel", false, false, List.of()));

        expectedUser = userService.changePassword(jwtService.createJwt(new UserPassDTO("Rachel", false)), new ChangePasswordDTO("9876", "02_04_1999"));

        result = userControllerImp.changePassword(jwtService.createJwt(new UserPassDTO("Rachel", false)), new ChangePasswordDTO("9876", "02_04_1999"));

        assertThat(new ResponseEntity<>(expectedUser, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(result);


        when(userService.changePassword(jwtService.createJwt(new UserPassDTO("Dinamo", false)), new ChangePasswordDTO("hellowordl", "84l0nc3s70"))).thenReturn(new UserDTO("Dinamo", false, false, List.of()));

        expectedUser = userService.changePassword(jwtService.createJwt(new UserPassDTO("Dinamo", false)), new ChangePasswordDTO("hellowordl", "84l0nc3s70"));

        result = userControllerImp.changePassword(jwtService.createJwt(new UserPassDTO("Dinamo", false)), new ChangePasswordDTO("hellowordl", "84l0nc3s70"));

        assertThat(new ResponseEntity<>(expectedUser, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(result);


        when(userService.changePassword(jwtService.createJwt(new UserPassDTO("Houdini", false)), new ChangePasswordDTO("aaa", "IwiCcR!7fOdIiNkE?6"))).thenReturn(new UserDTO("Houdini", false, false, List.of()));

        expectedUser = userService.changePassword(jwtService.createJwt(new UserPassDTO("Houdini", false)), new ChangePasswordDTO("aaa", "IwiCcR!7fOdIiNkE?6"));

        result = userControllerImp.changePassword(jwtService.createJwt(new UserPassDTO("Houdini", false)), new ChangePasswordDTO("aaa", "IwiCcR!7fOdIiNkE?6"));

        assertThat(new ResponseEntity<>(expectedUser, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(result);

    }


    @ParameterizedTest
    @ValueSource(strings = {"d3llano", "3than", "19grace", "v1olet", "333"})
    void changeUserPasswordTestUserNotFound(String username) {

        when(userService.changePassword(jwtService.createJwt(new UserPassDTO(username, false)), new ChangePasswordDTO("1234", "9876secure"))).thenThrow(UserNotFoundException.class);

        ResponseEntity<UserDTO> result = userControllerImp.changePassword(jwtService.createJwt(new UserPassDTO(username, false)), new ChangePasswordDTO("1234", "9876secure"));

        assertThat(new ResponseEntity<>(HttpStatus.NOT_FOUND)).usingRecursiveComparison().isEqualTo(result);

    }

    @ParameterizedTest
    @ValueSource(strings = {"Dellano", "ethan", "grace19", "violet12", "eeee"})
    void changeUserPasswordTestPasswordNotValid(String username) {

        when(userService.changePassword(jwtService.createJwt(new UserPassDTO(username, false)), new ChangePasswordDTO("9876", ""))).thenThrow(PasswordNotValidException.class);

        ResponseEntity<UserDTO> result = userControllerImp.changePassword(jwtService.createJwt(new UserPassDTO(username, false)), new ChangePasswordDTO("9876", ""));

        assertThat(new ResponseEntity<>(HttpStatus.NOT_MODIFIED)).usingRecursiveComparison().isEqualTo(result);

    }

    @ParameterizedTest
    @ValueSource(strings = {"Dellano", "ethan", "grace19", "violet12", "eeee"})
    void changeUserPasswordTestIncorrectPassword(String username) {

        when(userService.changePassword(jwtService.createJwt(new UserPassDTO(username, false)), new ChangePasswordDTO("hello", "1234securePassword9876"))).thenThrow(IncorrectPasswordException.class);

        ResponseEntity<UserDTO> result = userControllerImp.changePassword(jwtService.createJwt(new UserPassDTO(username, false)), new ChangePasswordDTO("hello", "1234securePassword9876"));

        assertThat(new ResponseEntity<>(HttpStatus.BAD_REQUEST)).usingRecursiveComparison().isEqualTo(result);

    }

}
