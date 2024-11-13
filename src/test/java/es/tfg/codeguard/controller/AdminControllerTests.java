package es.tfg.codeguard.controller;

import es.tfg.codeguard.controller.imp.AdminControllerImp;
import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.service.AdminService;
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

    @BeforeEach
    void setup() {
        userDTO = new UserDTO();
        userDTO.setTester(false);
        userDTO.setCreator(false);

    }


    @ParameterizedTest
    @ValueSource(strings = {"FirstUser", "SecondUser", "ThirdUser", "FourthUser"})
    void deleteUserByIdTest(String userName) {

        when(adminService.deleteUser(userName)).thenReturn(Optional.ofNullable(userDTO));

        Optional<UserDTO> resultado = adminService.deleteUser(userName);

        ResponseEntity<UserDTO> esperado = adminControllerImp.deleteUser(userName);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.OK));

    }

    @ParameterizedTest
    @ValueSource(strings = {"FirstUser", "SecondUser", "ThirdUser", "FourthUser"})
    void InvalidDeleteUserByIdTest(String userName) {

        when(adminService.deleteUser(userName)).thenReturn(Optional.empty());

        ResponseEntity<UserDTO> esperado = adminControllerImp.deleteUser(userName);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

}
