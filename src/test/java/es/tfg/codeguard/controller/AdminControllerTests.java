package es.tfg.codeguard.controller;

import es.tfg.codeguard.controller.imp.AdminControllerImp;
import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.service.AdminService;
import es.tfg.codeguard.util.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

    private UserDTO userDTO;

    @BeforeEach
    void setup() {
        userDTO = new UserDTO("", false, false, new java.util.ArrayList<>());
    }

    @ParameterizedTest
    @ValueSource(strings = {"FirstUser", "SecondUser", "ThirdUser", "FourthUser"})
    void deleteUserByIdTest(String username) {

        when(adminService.deleteUser(username)).thenReturn(userDTO);

        UserDTO resultado = adminService.deleteUser(username);

        ResponseEntity<UserDTO> esperado = adminControllerImp.deleteUser(username);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.OK));

    }

    @ParameterizedTest
    @ValueSource(strings = {"FirstUser", "SecondUser", "ThirdUser", "FourthUser"})
    void InvalidDeleteUserByIdTest(String username) {

        when(adminService.deleteUser(username)).thenThrow(new UserNotFoundException("User not found [" + username + "]"));

        ResponseEntity<UserDTO> esperado = adminControllerImp.deleteUser(username);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

}
