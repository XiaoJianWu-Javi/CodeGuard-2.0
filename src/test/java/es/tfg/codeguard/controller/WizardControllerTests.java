package es.tfg.codeguard.controller;

import es.tfg.codeguard.model.dto.PasswordWizardDTO;
import es.tfg.codeguard.model.dto.WizardDTO;
import es.tfg.codeguard.service.WizardService;
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


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
//@SpringBootTest
class WizardControllerTests {

    @Mock
    private WizardService wizardService;

    @InjectMocks
    //@AutoWired
    private WizardController wizardController;

    @Mock
    WizardDTO wizardDTO;

    @Mock
    PasswordWizardDTO passwordWizardDTO;

    @Test
    void registerWizardTest() {

//        when(wizardService.registerWizard("FirstWizard", "1234")).thenReturn(Optional.ofNullable(wizardDTO));
//
//        Optional<WizardDTO> resultado = wizardService.registerWizard("FirstWizard", "1234");
//
//        ResponseEntity<Optional<WizardDTO>> esperado = wizardController.registerWizard("FirstWizard", "1234");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.CREATED));
//
//
//        when(wizardService.registerWizard("WizardSecond", "9876")).thenReturn(Optional.ofNullable(wizardDTO));
//
//        resultado = wizardService.registerWizard("WizardSecond", "9876");
//
//        esperado = wizardController.registerWizard("WizardSecond", "9876");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.CREATED));
//
//
//        when(wizardService.registerWizard("Wizard3", "bestWizard123")).thenReturn(Optional.ofNullable(wizardDTO));
//
//        resultado = wizardService.registerWizard("Wizard3", "bestWizard123");
//
//        esperado = wizardController.registerWizard("Wizard3", "bestWizard123");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.CREATED));

            when(wizardService.registerWizard("FirstWizard", "1234")).thenReturn(Optional.ofNullable(passwordWizardDTO));

            Optional<PasswordWizardDTO> resultado = wizardService.registerWizard("FirstWizard", "1234");



            ResponseEntity<Optional<PasswordWizardDTO>> esperado = wizardController.registerWizard("FirstWizard", "1234");

            assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.CREATED));


    }

//    @Test
//    void invalidRegisterWizardTest() {
//
//        when(wizardService.registerWizard("FirstWizardñ", "1234")).thenReturn(Optional.empty());
//
//        Optional<WizardDTO> resultado = wizardService.registerWizard("FirstWizardñ", "1234");
//
//        ResponseEntity<Optional<WizardDTO>> esperado = wizardController.registerWizard("FirstWizardñ", "1234");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND));
//
//
//        when(wizardService.registerWizard("WizardSecond<?php>", "9876")).thenReturn(Optional.empty());
//
//        resultado = wizardService.registerWizard("WizardSecond<?php>", "9876");
//
//        esperado = wizardController.registerWizard("WizardSecond<?php>", "9876");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND));
//
//
//        when(wizardService.registerWizard("Wizard3;;:.+", "bestWizard123")).thenReturn(Optional.empty());
//
//        resultado = wizardService.registerWizard("Wizard3;;:.+", "bestWizard123");
//
//        esperado = wizardController.registerWizard("Wizard3;;:.+", "bestWizard123");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND));
//
//    }
//
//    @Test
//    void loginWizardTest() {
//
//        when(wizardService.loginWizard("FirstWizard", "1234")).thenReturn(Optional.ofNullable(wizardDTO));
//
//        Optional<WizardDTO> resultado = wizardService.loginWizard("FirstWizard", "1234");
//
//        ResponseEntity<Optional<WizardDTO>> esperado = wizardController.loginWizard("FirstWizard", "1234");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.OK));
//
//
//        when(wizardService.loginWizard("WizardSecond", "9876")).thenReturn(Optional.ofNullable(wizardDTO));
//
//        resultado = wizardService.loginWizard("WizardSecond", "9876");
//
//        esperado = wizardController.loginWizard("WizardSecond", "9876");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.OK));
//
//
//        when(wizardService.loginWizard("Wizard3", "bestWizard123")).thenReturn(Optional.ofNullable(wizardDTO));
//
//        resultado = wizardService.loginWizard("Wizard3", "bestWizard123");
//
//        esperado = wizardController.loginWizard("Wizard3", "bestWizard123");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.OK));
//
//    }
//
//    @Test
//    void InvalidloginWizardTest() {
//
//        when(wizardService.loginWizard("FirstWizardñ", "1234")).thenReturn(Optional.empty());
//
//        Optional<WizardDTO> resultado = wizardService.loginWizard("FirstWizardñ", "1234");
//
//        ResponseEntity<Optional<WizardDTO>> esperado = wizardController.loginWizard("FirstWizardñ", "1234");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND));
//
//
//        when(wizardService.loginWizard("WizardSecond", "9876")).thenReturn(Optional.empty());
//
//        resultado = wizardService.loginWizard("WizardSecond", "9876");
//
//        esperado = wizardController.loginWizard("WizardSecond", "9876");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND));
//
//
//        when(wizardService.loginWizard("Wizard3", "bestWizard123")).thenReturn(Optional.empty());
//
//        resultado = wizardService.loginWizard("Wizard3", "bestWizard123");
//
//        esperado = wizardController.loginWizard("Wizard3", "bestWizard123");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND));
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"FirstWizard","SecondWizard","ThirdWizard","FourthWizard"})
//    void deleteWizardTest(String wizardName) {
//
//        when(wizardService.deleteWizard(wizardName)).thenReturn(Optional.ofNullable(wizardDTO));
//
//        Optional<WizardDTO> resultado = wizardService.deleteWizard(wizardName);
//
//        ResponseEntity<Optional<WizardDTO>> esperado = wizardController.deleteWizard(wizardName);
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.OK));
//
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"FirstWizard","SecondWizard","ThirdWizard","FourthWizard"})
//    void InvalidDeleteWizardTest(String wizardName) {
//
//        when(wizardService.deleteWizard(wizardName)).thenReturn(Optional.empty());
//
//        Optional<WizardDTO> resultado = wizardService.deleteWizard(wizardName);
//
//        ResponseEntity<Optional<WizardDTO>> esperado = wizardController.deleteWizard(wizardName);
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND));
//
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"aaron_pp","saul_af","dani_ro","diego_ra"})
//    void GetWizardByIdTest(String wizardName) {
//
//        when(wizardService.getWizardById(wizardName)).thenReturn(Optional.ofNullable(wizardDTO));
//
//        Optional<WizardDTO> resultado = wizardService.getWizardById(wizardName);
//
//        ResponseEntity<Optional<WizardDTO>> esperado = wizardController.getWizardById(wizardName);
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.OK));
//
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"huwhqñ", "duhqñidqidiq", "qi@dqiqdqdmiqdnqijdqjq", "#iuqdqdq", "uhy@", "_adam", "ad_am", "adam_#", "udhwqduqid???", "?¿ñ", "ññññññ", "adam#¿?!¡"})
//    void InvalidGetWizardByIdTest(String wizardName) {
//
//        when(wizardService.getWizardById(wizardName)).thenReturn(Optional.empty());
//
//        Optional<WizardDTO> resultado = wizardService.getWizardById(wizardName);
//
//        ResponseEntity<Optional<WizardDTO>> esperado = wizardController.getWizardById(wizardName);
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND));
//
//    }
//
//    @Test
//    void UpdateWizardPasswordTest(){
//
//        when(wizardService.updateWizard("FirstWizard", "1234new")).thenReturn(Optional.ofNullable(wizardDTO));
//
//        Optional<WizardDTO> resultado = wizardService.updateWizard("FirstWizard", "1234new");
//
//        ResponseEntity<Optional<WizardDTO>> esperado = wizardController.updateWizard("FirstWizard", "1234new");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.OK));
//
//        when(wizardService.updateWizard("SecondWizard", "9876new")).thenReturn(Optional.ofNullable(wizardDTO));
//
//        resultado = wizardService.updateWizard("SecondWizard", "9876new");
//
//        esperado = wizardController.updateWizard("SecondWizard", "9876new");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.OK));
//
//
//        when(wizardService.updateWizard("ThirdtWizard", "newpass")).thenReturn(Optional.ofNullable(wizardDTO));
//
//        resultado = wizardService.updateWizard("ThirdtWizard", "newpass");
//
//        esperado = wizardController.updateWizard("ThirdtWizard", "newpass");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.OK));
//
//
//        when(wizardService.updateWizard("FourthWizard", "newhola1234")).thenReturn(Optional.ofNullable(wizardDTO));
//
//        resultado = wizardService.updateWizard("FourthWizard", "newhola1234");
//
//        esperado = wizardController.updateWizard("FourthWizard", "newhola1234");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.OK));
//
//    }
//
//    @Test
//    void InvalidUpdateWizardPasswordTest(){
//
//        when(wizardService.updateWizard("FirstWizard", "new1234;;")).thenReturn(Optional.empty());
//
//        Optional<WizardDTO> resultado = wizardService.updateWizard("FirstWizard", "new1234;;");
//
//        ResponseEntity<Optional<WizardDTO>> esperado = wizardController.updateWizard("FirstWizard", "new1234;;");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.NOT_MODIFIED));
//
//
//        when(wizardService.updateWizard("SecondWizard", "newhola1234ºº")).thenReturn(Optional.empty());
//
//        resultado = wizardService.updateWizard("SecondWizard", "newhola1234ºº");
//
//        esperado = wizardController.updateWizard("SecondWizard", "newhola1234ºº");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.NOT_MODIFIED));
//
//
//        when(wizardService.updateWizard("ThirdWizard", "ªªaaaa")).thenReturn(Optional.empty());
//
//        resultado = wizardService.updateWizard("ThirdWizard", "ªªaaa");
//
//        esperado = wizardController.updateWizard("ThirdWizard", "ªªaaaa");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.NOT_MODIFIED));
//
//
//        when(wizardService.updateWizard("FourthWizard", "juitenDiten,,,,")).thenReturn(Optional.empty());
//
//        resultado = wizardService.updateWizard("FourthWizard", "juitenDiten,,,,");
//
//        esperado = wizardController.updateWizard("FourthWizard", "juitenDiten,,,,");
//
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.NOT_MODIFIED));
//
//    }

}
