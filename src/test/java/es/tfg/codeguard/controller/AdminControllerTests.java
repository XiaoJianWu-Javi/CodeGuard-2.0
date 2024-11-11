package es.tfg.codeguard.controller;

import es.tfg.codeguard.controller.imp.AdminControllerImp;
import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.service.AdminService;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class AdminControllerTests {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminControllerImp adminControllerImp;


    private UserDTO userDTO;
    private UserPassDTO userPassDTO;


    @BeforeEach
    void setup() {
        userDTO = new UserDTO();
        userDTO.setTester(false);
        userDTO.setCreator(false);

        userPassDTO = new UserPassDTO();
        userPassDTO.setAdmin(false);

    }


    @ParameterizedTest
    @ValueSource(strings = {"FirstWizard", "SecondWizard", "ThirdWizard", "FourthWizard"})
    void deleteWizardByIdTest(String wizardName) {

        when(adminService.deleteUser(wizardName)).thenReturn(Optional.ofNullable(userDTO));

        Optional<UserDTO> resultado = adminService.deleteUser(wizardName);

        ResponseEntity<UserDTO> esperado = adminControllerImp.deleteUser(wizardName);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.OK));

    }

    @ParameterizedTest
    @ValueSource(strings = {"FirstWizard", "SecondWizard", "ThirdWizard", "FourthWizard"})
    void InvalidDeleteWizardByIdTest(String wizardName) {

        when(adminService.deleteUser(wizardName)).thenReturn(Optional.empty());

        ResponseEntity<UserDTO> esperado = adminControllerImp.deleteUser(wizardName);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @Test
    void UpdateWizardPasswordTest() {

        userPassDTO.setUsername("FirstWizard");

        when(adminService.updateUser("FirstWizard", "1234new")).thenReturn(Optional.ofNullable(userPassDTO));

        Optional<UserPassDTO> resultado = adminService.updateUser("FirstWizard", "1234new");

        ResponseEntity<UserPassDTO> esperado = adminControllerImp.updateUser("FirstWizard", "1234new");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.OK));


        userPassDTO.setUsername("SecondWizard");

        when(adminService.updateUser("SecondWizard", "9876new")).thenReturn(Optional.ofNullable(userPassDTO));

        resultado = adminService.updateUser("SecondWizard", "9876new");

        esperado = adminControllerImp.updateUser("SecondWizard", "9876new");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.OK));


        userPassDTO.setUsername("ThirdtWizard");

        when(adminService.updateUser("ThirdtWizard", "newpass")).thenReturn(Optional.ofNullable(userPassDTO));

        resultado = adminService.updateUser("ThirdtWizard", "newpass");

        esperado = adminControllerImp.updateUser("ThirdtWizard", "newpass");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.OK));


        userPassDTO.setUsername("FourthWizard");

        when(adminService.updateUser("FourthWizard", "newhola1234")).thenReturn(Optional.ofNullable(userPassDTO));

        resultado = adminService.updateUser("FourthWizard", "newhola1234");

        esperado = adminControllerImp.updateUser("FourthWizard", "newhola1234");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.OK));

    }

    @Test
    void InvalidUpdateWizardPasswordTest() {

        when(adminService.updateUser("FirstWizard", "new1234;;")).thenReturn(Optional.empty());

        Optional<UserPassDTO> resultado = adminService.updateUser("FirstWizard", "new1234;;");

        ResponseEntity<UserPassDTO> esperado = adminControllerImp.updateUser("FirstWizard", "new1234;;");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.NOT_MODIFIED));


        when(adminService.updateUser("SecondWizard", "newhola1234ºº")).thenReturn(Optional.empty());

        resultado = adminService.updateUser("SecondWizard", "newhola1234ºº");

        esperado = adminControllerImp.updateUser("SecondWizard", "newhola1234ºº");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.NOT_MODIFIED));


        when(adminService.updateUser("ThirdWizard", "ªªaaaa")).thenReturn(Optional.empty());

        resultado = adminService.updateUser("ThirdWizard", "ªªaaa");

        esperado = adminControllerImp.updateUser("ThirdWizard", "ªªaaaa");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.NOT_MODIFIED));


        when(adminService.updateUser("FourthWizard", "juitenDiten,,,,")).thenReturn(Optional.empty());

        resultado = adminService.updateUser("FourthWizard", "juitenDiten,,,,");

        esperado = adminControllerImp.updateUser("FourthWizard", "juitenDiten,,,,");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.NOT_MODIFIED));

    }

//TODO: Incremento de código Strint 2

//
//	@Test
//	void getAllUsersTest() {
//
//		when(elderService.getAllWizards()).thenReturn(new ArrayList<>());
//
//		List<WizardDTO> resultado = elderService.getAllWizards();
//
//		ResponseEntity<List<WizardDTO>> esperado = elderController.getAllWizards();
//
//		assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.OK));
//
//	}
//
//	@Test
//	void InvalidGetAllUsersTest() {
//
//		when(elderService.getAllWizards()).thenReturn(new ArrayList<>());
//
//		List<WizardDTO> resultado = elderService.getAllWizards();
//
//		ResponseEntity<List<WizardDTO>> esperado = elderController.getAllWizards();
//
//		assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND));
//
//	}

}
