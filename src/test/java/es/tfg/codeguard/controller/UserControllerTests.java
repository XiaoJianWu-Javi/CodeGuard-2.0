package es.tfg.codeguard.controller;

import es.tfg.codeguard.controller.imp.UserControllerImp;
import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.service.AdminService;
import es.tfg.codeguard.service.UserService;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserControllerTests {

    @Mock
    private UserService userService;

    @Mock
    private AdminService adminService;

    @InjectMocks
    private UserControllerImp userControllerImp;

    private UserDTO userDTO;
    private UserPassDTO userPassDTO;
    @ParameterizedTest
    @ValueSource(strings = {"FirstUser", "SecondUser", "ThirdUser", "FourthUser"})
    void deleteUserTest(String userName) {

        userDTO = new UserDTO(userName, false, false, new ArrayList<>());

        when(userService.deleteUser(userName)).thenReturn(Optional.ofNullable(userDTO));

        Optional<UserDTO> resultado = userService.deleteUser(userName);

        ResponseEntity<UserDTO> esperado = userControllerImp.deleteUser(userName);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.OK));

    }

    @ParameterizedTest
    @ValueSource(strings = {"FirstUser", "SecondUser", "ThirdUser", "FourthUser"})
    void InvalidDeleteUserTest(String userName) {

        when(userService.deleteUser(userName)).thenReturn(Optional.empty());

        ResponseEntity<UserDTO> esperado = userControllerImp.deleteUser(userName);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @ParameterizedTest
    @ValueSource(strings = {"aaron_pp", "saul_af", "dani_ro", "diego_ra"})
    void GetUserByIdTest(String userName) {

        userDTO = new UserDTO(userName, false, false, new ArrayList<>());

        when(userService.getUserById(userName)).thenReturn(Optional.ofNullable(userDTO));

        Optional<UserDTO> resultado = userService.getUserById(userName);

        ResponseEntity<UserDTO> esperado = userControllerImp.getUserById(userName);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.OK));

    }

    @ParameterizedTest
    @ValueSource(strings = {"huwhqñ", "duhqñidqidiq", "qi@dqiqdqdmiqdnqijdqjq", "#iuqdqdq", "uhy@", "_adam", "ad_am", "adam_#", "udhwqduqid???", "?¿ñ", "ññññññ", "adam#¿?!¡"})
    void InvalidGetUserByIdTest(String userName) {

        when(userService.getUserById(userName)).thenReturn(Optional.empty());

        ResponseEntity<UserDTO> esperado = userControllerImp.getUserById(userName);

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

        userPassDTO = new UserPassDTO("FirstUser", false);

        when(adminService.updateUser("FirstUser", "1234new")).thenReturn(Optional.ofNullable(userPassDTO));

        Optional<UserPassDTO> resultado = adminService.updateUser("FirstUser", "1234new");

        ResponseEntity<UserPassDTO> esperado = userControllerImp.updateUser("FirstUser", "1234new");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.OK));


        userPassDTO = new UserPassDTO("SecondUser", false);

        when(adminService.updateUser("SecondUser", "9876new")).thenReturn(Optional.ofNullable(userPassDTO));

        resultado = adminService.updateUser("SecondUser", "9876new");

        esperado = userControllerImp.updateUser("SecondUser", "9876new");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.OK));


        userPassDTO = new UserPassDTO("ThirdtUser", false);

        when(adminService.updateUser("ThirdtUser", "newpass")).thenReturn(Optional.ofNullable(userPassDTO));

        resultado = adminService.updateUser("ThirdtUser", "newpass");

        esperado = userControllerImp.updateUser("ThirdtUser", "newpass");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.OK));


        userPassDTO = new UserPassDTO("FourthUser", false);

        when(adminService.updateUser("FourthUser", "newhola1234")).thenReturn(Optional.ofNullable(userPassDTO));

        resultado = adminService.updateUser("FourthUser", "newhola1234");

        esperado = userControllerImp.updateUser("FourthUser", "newhola1234");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.OK));

    }

    @Test
    void InvalidUpdateUserPasswordTest() {

        when(adminService.updateUser("FirstUser", "new1234;;")).thenReturn(Optional.empty());

        Optional<UserPassDTO> resultado = adminService.updateUser("FirstUser", "new1234;;");

        ResponseEntity<UserPassDTO> esperado = userControllerImp.updateUser("FirstUser", "new1234;;");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.NOT_MODIFIED));


        when(adminService.updateUser("SecondUser", "newhola1234ºº")).thenReturn(Optional.empty());

        resultado = adminService.updateUser("SecondUser", "newhola1234ºº");

        esperado = userControllerImp.updateUser("SecondUser", "newhola1234ºº");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.NOT_MODIFIED));


        when(adminService.updateUser("ThirdUser", "ªªaaaa")).thenReturn(Optional.empty());

        resultado = adminService.updateUser("ThirdUser", "ªªaaa");

        esperado = userControllerImp.updateUser("ThirdUser", "ªªaaaa");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.NOT_MODIFIED));


        when(adminService.updateUser("FourthUser", "juitenDiten,,,,")).thenReturn(Optional.empty());

        resultado = adminService.updateUser("FourthUser", "juitenDiten,,,,");

        esperado = userControllerImp.updateUser("FourthUser", "juitenDiten,,,,");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.NOT_MODIFIED));

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
