package es.tfg.codeguard.controller;

import es.tfg.codeguard.controller.imp.UserControllerImp;
import es.tfg.codeguard.model.dto.ChangePasswordDTO;
import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.model.entity.user.User;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    private UserPassDTO userPassDTO;

    @ParameterizedTest
    @ValueSource(strings = {"FirstUser", "SecondUser", "ThirdUser", "FourthUser"})
    void deleteUserTest(String username) {

        userDTO = new UserDTO(username, false, false, new ArrayList<>());

        when(userService.deleteUser(username)).thenReturn(userDTO);

        UserDTO resultado = userService.deleteUser(username);

        ResponseEntity<UserDTO> esperado = userControllerImp.deleteUser(username);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.OK));

    }

    @ParameterizedTest
    @ValueSource(strings = {"FirstUser", "SecondUser", "ThirdUser", "FourthUser"})
    void InvalidDeleteUserTest(String username) {

        when(userService.deleteUser(username)).thenThrow(new UserNotFoundException("User not found [ " + username + " ]"));

        ResponseEntity<UserDTO> esperado = userControllerImp.deleteUser(username);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @ParameterizedTest
    @ValueSource(strings = {"aaron_pp", "saul_af", "dani_ro", "diego_ra"})
    void GetUserByIdTest(String username) {

        userDTO = new UserDTO(username, false, false, new ArrayList<>());

        when(userService.getUserById(username)).thenReturn(userDTO);

        UserDTO resultado = userService.getUserById(username);

        ResponseEntity<UserDTO> esperado = userControllerImp.getUserById(username);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.OK));

    }

    @ParameterizedTest
    @ValueSource(strings = {"huwhqñ", "duhqñidqidiq", "qi@dqiqdqdmiqdnqijdqjq", "#iuqdqdq", "uhy@", "_adam", "ad_am", "adam_#", "udhwqduqid???", "?¿ñ", "ññññññ", "adam#¿?!¡"})
    void InvalidGetUserByIdTest(String username) {

        when(userService.getUserById(username)).thenThrow(new UserNotFoundException("Username not valid [" + username + "]"));

        ResponseEntity<UserDTO> esperado = userControllerImp.getUserById(username);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @Test
    void GetAllUsersTest() {

        UserDTO user1 = new UserDTO("user1", false, false, new ArrayList<>());
        UserDTO user2 = new UserDTO("user2", false, false, new ArrayList<>());
        UserDTO user3 = new UserDTO("user3", false, false, new ArrayList<>());


        when(userService.getAllUsers()).thenReturn(new ArrayList<UserDTO>(Arrays.asList()));

        List<UserDTO> resultado = userService.getAllUsers();

        ResponseEntity<List<UserDTO>> esperado = userControllerImp.getAllUser();

        Assertions.assertEquals(0, resultado.size());
        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.OK));


        when(userService.getAllUsers()).thenReturn(new ArrayList<UserDTO>(Arrays.asList(user1)));

        resultado = userService.getAllUsers();

        esperado = userControllerImp.getAllUser();

        Assertions.assertEquals(1, resultado.size());
        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.OK));


        when(userService.getAllUsers()).thenReturn(new ArrayList<UserDTO>(Arrays.asList(user1, user2)));

        resultado = userService.getAllUsers();

        esperado = userControllerImp.getAllUser();

        Assertions.assertEquals(2, resultado.size());
        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.OK));


        when(userService.getAllUsers()).thenReturn(new ArrayList<UserDTO>(Arrays.asList(user1, user2, user3)));

        resultado = userService.getAllUsers();

        esperado = userControllerImp.getAllUser();

        Assertions.assertEquals(3, resultado.size());
        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.OK));

    }

    @Test
    void UpdateUserPasswordTest() {

        when(userService.changePassword(jwtService.createJwt(new UserPassDTO("Damian", false)), new ChangePasswordDTO("1234", "newSecurePassword1234"))).thenReturn(new UserDTO("Damian",false,false,List.of()));

        UserDTO expectedUser = userService.changePassword(jwtService.createJwt(new UserPassDTO("Damian", false)), new ChangePasswordDTO("1234", "newSecurePassword1234"));

        ResponseEntity<UserDTO> result = userControllerImp.changePassword(jwtService.createJwt(new UserPassDTO("Damian", false)), new ChangePasswordDTO("1234", "newSecurePassword1234"));

        assertThat(new ResponseEntity<>(expectedUser, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(result);


        when(userService.changePassword(jwtService.createJwt(new UserPassDTO("Rachel", false)), new ChangePasswordDTO("9876", "02_04_1999"))).thenReturn(new UserDTO("Rachel",false,false,List.of()));

        expectedUser = userService.changePassword(jwtService.createJwt(new UserPassDTO("Rachel", false)), new ChangePasswordDTO("9876", "02_04_1999"));

        result = userControllerImp.changePassword(jwtService.createJwt(new UserPassDTO("Rachel", false)), new ChangePasswordDTO("9876", "02_04_1999"));

        assertThat(new ResponseEntity<>(expectedUser, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(result);


        when(userService.changePassword(jwtService.createJwt(new UserPassDTO("Dinamo", false)), new ChangePasswordDTO("hellowordl", "84l0nc3s70"))).thenReturn(new UserDTO("Dinamo",false,false,List.of()));

        expectedUser = userService.changePassword(jwtService.createJwt(new UserPassDTO("Dinamo", false)), new ChangePasswordDTO("hellowordl", "84l0nc3s70"));

        result = userControllerImp.changePassword(jwtService.createJwt(new UserPassDTO("Dinamo", false)), new ChangePasswordDTO("hellowordl", "84l0nc3s70"));

        assertThat(new ResponseEntity<>(expectedUser, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(result);


        when(userService.changePassword(jwtService.createJwt(new UserPassDTO("Houdini", false)), new ChangePasswordDTO("aaa", "IwiCcR!7fOdIiNkE?6"))).thenReturn(new UserDTO("Houdini",false,false,List.of()));

        expectedUser = userService.changePassword(jwtService.createJwt(new UserPassDTO("Houdini", false)), new ChangePasswordDTO("aaa", "IwiCcR!7fOdIiNkE?6"));

        result = userControllerImp.changePassword(jwtService.createJwt(new UserPassDTO("Houdini", false)), new ChangePasswordDTO("aaa", "IwiCcR!7fOdIiNkE?6"));

        assertThat(new ResponseEntity<>(expectedUser, HttpStatus.OK)).usingRecursiveComparison().isEqualTo(result);

    }



    @ParameterizedTest
    @ValueSource(strings = {"d3llano","3than","19grace","v1olet","333"})
    void InvalidChangePasswordUserNotFoundTest(String username) {

        when(userService.changePassword(jwtService.createJwt(new UserPassDTO(username, false)), new ChangePasswordDTO("1234", "9876secure"))).thenThrow(UserNotFoundException.class);

        ResponseEntity<UserDTO> result = userControllerImp.changePassword(jwtService.createJwt(new UserPassDTO(username, false)), new ChangePasswordDTO("1234", "9876secure"));

        assertThat(new ResponseEntity<>(HttpStatus.NOT_FOUND)).usingRecursiveComparison().isEqualTo(result);

    }

    @ParameterizedTest
    @ValueSource(strings = {"Dellano","ethan","grace19","violet12","eeee"})
    void InvalidChangePasswordPaswordNotValidTest(String username) {

        when(userService.changePassword(jwtService.createJwt(new UserPassDTO(username, false)), new ChangePasswordDTO("9876", ""))).thenThrow(PasswordNotValidException.class);

        ResponseEntity<UserDTO> result = userControllerImp.changePassword(jwtService.createJwt(new UserPassDTO(username, false)), new ChangePasswordDTO("9876", ""));

        assertThat(new ResponseEntity<>(HttpStatus.NOT_MODIFIED)).usingRecursiveComparison().isEqualTo(result);

    }

    @ParameterizedTest
    @ValueSource(strings = {"Dellano","ethan","grace19","violet12","eeee"})
    void InvalidChangePasswordIncorrectPasswordTest(String username) {

        when(userService.changePassword(jwtService.createJwt(new UserPassDTO(username, false)), new ChangePasswordDTO("hello", "1234securePassword9876"))).thenThrow(IncorrectPasswordException.class);

        ResponseEntity<UserDTO> result = userControllerImp.changePassword(jwtService.createJwt(new UserPassDTO(username, false)), new ChangePasswordDTO("hello", "1234securePassword9876"));

        assertThat(new ResponseEntity<>(HttpStatus.BAD_REQUEST)).usingRecursiveComparison().isEqualTo(result);

    }


//TODO: Esperar a implementación el en servicio correcto

//    @Test
//    void UpdateUserPasswordTest(){
//
//        when(userService.updateUserPass("FirstUser", "1234new")).thenReturn(Optional.ofNullable(userDTO));
//
//        Optional<UserDTO> resultado = userService.updateUserPass("FirstUser", "1234new");
//
//        ResponseEntity<UserDTO> esperado = userControllerImp.updateUserPass("FirstUser", "1234new");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.OK));
//
//        when(userService.updateUserPass("SecondUser", "9876new")).thenReturn(Optional.ofNullable(userDTO));
//
//        resultado = userService.updateUserPass("SecondUser", "9876new");
//
//        esperado = userControllerImp.updateUserPass("SecondUser", "9876new");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.OK));
//
//
//        when(userService.updateUserPass("ThirdtUser", "newpass")).thenReturn(Optional.ofNullable(userDTO));
//
//        resultado = userService.updateUserPass("ThirdtUser", "newpass");
//
//        esperado = userControllerImp.updateUserPass("ThirdtUser", "newpass");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.OK));
//
//
//        when(userService.updateUserPass("FourthUser", "newhola1234")).thenReturn(Optional.ofNullable(userDTO));
//
//        resultado = userService.updateUserPass("FourthUser", "newhola1234");
//
//        esperado = userControllerImp.updateUserPass("FourthUser", "newhola1234");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.OK));
//
//    }
//
//    @Test
//    void InvalidUpdateUserPasswordTest(){
//
//        when(userService.updateUserPass("FirstUser", "new1234;;")).thenReturn(Optional.empty());
//
//        Optional<UserDTO> resultado = userService.updateUserPass("FirstUser", "new1234;;");
//
//        ResponseEntity<UserDTO> esperado = userControllerImp.updateUserPass("FirstUser", "new1234;;");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.NOT_MODIFIED));
//
//
//        when(userService.updateUserPass("SecondUser", "newhola1234ºº")).thenReturn(Optional.empty());
//
//        resultado = userService.updateUserPass("SecondUser", "newhola1234ºº");
//
//        esperado = userControllerImp.updateUserPass("SecondUser", "newhola1234ºº");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.NOT_MODIFIED));
//
//
//        when(userService.updateUserPass("ThirdUser", "ªªaaaa")).thenReturn(Optional.empty());
//
//        resultado = userService.updateUserPass("ThirdUser", "ªªaaa");
//
//        esperado = userControllerImp.updateUserPass("ThirdUser", "ªªaaaa");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.NOT_MODIFIED));
//
//
//        when(userService.updateUserPass("FourthUser", "juitenDiten,,,,")).thenReturn(Optional.empty());
//
//        resultado = userService.updateUserPass("FourthUser", "juitenDiten,,,,");
//
//        esperado = userControllerImp.updateUserPass("FourthUser", "juitenDiten,,,,");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.NOT_MODIFIED));
//
//    }

}
