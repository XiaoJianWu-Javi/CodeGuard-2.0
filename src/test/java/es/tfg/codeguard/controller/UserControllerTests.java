package es.tfg.codeguard.controller;

import es.tfg.codeguard.controller.imp.UserControllerImp;
import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.service.UserService;
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

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserControllerTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserControllerImp userControllerImp;

    private UserDTO userDTO;
    private UserPassDTO userPassDTO;

    @BeforeEach
    void setup() {
        userPassDTO = new UserPassDTO();
        userPassDTO.setAdmin(false);

        userDTO = new UserDTO();
        userDTO.setTester(false);
        userDTO.setCreator(false);

    }

    @Test
    void registerWizardTest() {

        userPassDTO.setUsername("FirstWizard");

        when(userService.registerUser("FirstWizard", "1234")).thenReturn(Optional.of(userPassDTO));

        Optional<UserPassDTO> resultado = userService.registerUser("FirstWizard", "1234");

        ResponseEntity<UserPassDTO> esperado = userControllerImp.registerUser("FirstWizard", "1234");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.CREATED));


        userPassDTO.setUsername("WizardSecond");

        when(userService.registerUser("WizardSecond", "9876")).thenReturn(Optional.ofNullable(userPassDTO));

        resultado = userService.registerUser("WizardSecond", "9876");

        esperado = userControllerImp.registerUser("WizardSecond", "9876");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.CREATED));


        userPassDTO.setUsername("Wizard3");

        when(userService.registerUser("Wizard3", "bestWizard123")).thenReturn(Optional.ofNullable(userPassDTO));

        resultado = userService.registerUser("Wizard3", "bestWizard123");

        esperado = userControllerImp.registerUser("Wizard3", "bestWizard123");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.CREATED));

    }

    @Test
    void invalidRegisterWizardTest() {

        when(userService.registerUser("FirstWizardñ", "1234")).thenReturn(Optional.empty());

        ResponseEntity<UserPassDTO> esperado = userControllerImp.registerUser("FirstWizardñ", "1234");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.NOT_FOUND));


        when(userService.registerUser("WizardSecond<?php>", "9876")).thenReturn(Optional.empty());

        esperado = userControllerImp.registerUser("WizardSecond<?php>", "9876");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.NOT_FOUND));


        when(userService.registerUser("Wizard3;;:.+", "bestWizard123")).thenReturn(Optional.empty());

        esperado = userControllerImp.registerUser("Wizard3;;:.+", "bestWizard123");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @ParameterizedTest
    @ValueSource(strings = {"FirstWizard", "SecondWizard", "ThirdWizard", "FourthWizard"})
    void deleteWizardTest(String wizardName) {

        userDTO.setUsername(wizardName);

        when(userService.deleteUser(wizardName)).thenReturn(Optional.ofNullable(userDTO));

        Optional<UserDTO> resultado = userService.deleteUser(wizardName);

        ResponseEntity<UserDTO> esperado = userControllerImp.deleteUser(wizardName);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.OK));

    }

    @ParameterizedTest
    @ValueSource(strings = {"FirstWizard", "SecondWizard", "ThirdWizard", "FourthWizard"})
    void InvalidDeleteWizardTest(String wizardName) {

        when(userService.deleteUser(wizardName)).thenReturn(Optional.empty());

        ResponseEntity<UserDTO> esperado = userControllerImp.deleteUser(wizardName);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @ParameterizedTest
    @ValueSource(strings = {"aaron_pp", "saul_af", "dani_ro", "diego_ra"})
    void GetWizardByIdTest(String wizardName) {

        userDTO.setUsername(wizardName);

        when(userService.getUserById(wizardName)).thenReturn(Optional.ofNullable(userDTO));

        Optional<UserDTO> resultado = userService.getUserById(wizardName);

        ResponseEntity<UserDTO> esperado = userControllerImp.getUserById(wizardName);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.OK));

    }

    @ParameterizedTest
    @ValueSource(strings = {"huwhqñ", "duhqñidqidiq", "qi@dqiqdqdmiqdnqijdqjq", "#iuqdqdq", "uhy@", "_adam", "ad_am", "adam_#", "udhwqduqid???", "?¿ñ", "ññññññ", "adam#¿?!¡"})
    void InvalidGetWizardByIdTest(String wizardName) {

        when(userService.getUserById(wizardName)).thenReturn(Optional.empty());

        ResponseEntity<UserDTO> esperado = userControllerImp.getUserById(wizardName);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @Test
    void GetAllUsersTest() {

        UserDTO user1 = new UserDTO();
        user1.setUsername("user1");
        user1.setCreator(false);
        user1.setTester(false);

        UserDTO user2 = new UserDTO();
        user2.setUsername("user2");
        user2.setCreator(false);
        user2.setTester(false);

        UserDTO user3 = new UserDTO();
        user3.setUsername("user3");
        user3.setCreator(false);
        user3.setTester(false);

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

//TODO: Incremento de código Strint 2

//    @Test
//    void loginWizardTest() {
//
//        when(userService.loginUser("FirstWizard", "1234")).thenReturn(Optional.ofNullable(userDTO));
//
//        Optional<UserPassDTO> resultado = userService.loginWizard("FirstWizard", "1234");
//
//        ResponseEntity<Optional<WizardDTO>> esperado = userControllerImp.loginWizard("FirstWizard", "1234");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.OK));
//
//
//        when(userService.loginWizard("WizardSecond", "9876")).thenReturn(Optional.ofNullable(userDTO));
//
//        resultado = userService.loginWizard("WizardSecond", "9876");
//
//        esperado = userControllerImp.loginWizard("WizardSecond", "9876");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.OK));
//
//
//        when(userService.loginWizard("Wizard3", "bestWizard123")).thenReturn(Optional.ofNullable(userDTO));
//
//        resultado = userService.loginWizard("Wizard3", "bestWizard123");
//
//        esperado = userControllerImp.loginWizard("Wizard3", "bestWizard123");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.OK));
//
//    }
//
//    @Test
//    void InvalidloginWizardTest() {
//
//        when(userService.loginWizard("FirstWizardñ", "1234")).thenReturn(Optional.empty());
//
//        Optional<WizardDTO> resultado = userService.loginWizard("FirstWizardñ", "1234");
//
//        ResponseEntity<Optional<WizardDTO>> esperado = userControllerImp.loginWizard("FirstWizardñ", "1234");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.NOT_FOUND));
//
//
//        when(userService.loginWizard("WizardSecond", "9876")).thenReturn(Optional.empty());
//
//        resultado = userService.loginWizard("WizardSecond", "9876");
//
//        esperado = userControllerImp.loginWizard("WizardSecond", "9876");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.NOT_FOUND));
//
//
//        when(userService.loginWizard("Wizard3", "bestWizard123")).thenReturn(Optional.empty());
//
//        resultado = userService.loginWizard("Wizard3", "bestWizard123");
//
//        esperado = userControllerImp.loginWizard("Wizard3", "bestWizard123");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.NOT_FOUND));
//    }

//    @Test
//    void UpdateWizardPasswordTest(){
//
//        when(userService.updateUserPass("FirstWizard", "1234new")).thenReturn(Optional.ofNullable(userDTO));
//
//        Optional<UserDTO> resultado = userService.updateUserPass("FirstWizard", "1234new");
//
//        ResponseEntity<UserDTO> esperado = userControllerImp.updateUserPass("FirstWizard", "1234new");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.OK));
//
//        when(userService.updateUserPass("SecondWizard", "9876new")).thenReturn(Optional.ofNullable(userDTO));
//
//        resultado = userService.updateUserPass("SecondWizard", "9876new");
//
//        esperado = userControllerImp.updateUserPass("SecondWizard", "9876new");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.OK));
//
//
//        when(userService.updateUserPass("ThirdtWizard", "newpass")).thenReturn(Optional.ofNullable(userDTO));
//
//        resultado = userService.updateUserPass("ThirdtWizard", "newpass");
//
//        esperado = userControllerImp.updateUserPass("ThirdtWizard", "newpass");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.OK));
//
//
//        when(userService.updateUserPass("FourthWizard", "newhola1234")).thenReturn(Optional.ofNullable(userDTO));
//
//        resultado = userService.updateUserPass("FourthWizard", "newhola1234");
//
//        esperado = userControllerImp.updateUserPass("FourthWizard", "newhola1234");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.OK));
//
//    }
//
//    @Test
//    void InvalidUpdateWizardPasswordTest(){
//
//        when(userService.updateUserPass("FirstWizard", "new1234;;")).thenReturn(Optional.empty());
//
//        Optional<UserDTO> resultado = userService.updateUserPass("FirstWizard", "new1234;;");
//
//        ResponseEntity<UserDTO> esperado = userControllerImp.updateUserPass("FirstWizard", "new1234;;");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.NOT_MODIFIED));
//
//
//        when(userService.updateUserPass("SecondWizard", "newhola1234ºº")).thenReturn(Optional.empty());
//
//        resultado = userService.updateUserPass("SecondWizard", "newhola1234ºº");
//
//        esperado = userControllerImp.updateUserPass("SecondWizard", "newhola1234ºº");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.NOT_MODIFIED));
//
//
//        when(userService.updateUserPass("ThirdWizard", "ªªaaaa")).thenReturn(Optional.empty());
//
//        resultado = userService.updateUserPass("ThirdWizard", "ªªaaa");
//
//        esperado = userControllerImp.updateUserPass("ThirdWizard", "ªªaaaa");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.NOT_MODIFIED));
//
//
//        when(userService.updateUserPass("FourthWizard", "juitenDiten,,,,")).thenReturn(Optional.empty());
//
//        resultado = userService.updateUserPass("FourthWizard", "juitenDiten,,,,");
//
//        esperado = userControllerImp.updateUserPass("FourthWizard", "juitenDiten,,,,");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.NOT_MODIFIED));
//
//    }

}
