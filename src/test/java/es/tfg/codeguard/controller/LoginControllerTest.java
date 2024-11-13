package es.tfg.codeguard.controller;

import es.tfg.codeguard.controller.imp.LoginControllerImp;
import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.service.imp.LoginServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
public class LoginControllerTest {

    @Mock
    private LoginServiceImp loginServiceImp;

    @InjectMocks
    private LoginControllerImp loginControllerImp;

    private UserDTO userDTO;

    @BeforeEach
    void setup() {

        userDTO = new UserDTO();
        userDTO.setCreator(false);
        userDTO.setTester(false);

    }

    @Test
    void loginUserTest() {

        userDTO.setUsername("FirstUser");

        when(loginServiceImp.loginUser("FirstUser", "1234")).thenReturn(Optional.ofNullable(userDTO));

        Optional<UserDTO> resultado = loginServiceImp.loginUser("FirstUser", "1234");

        ResponseEntity<UserPassDTO> esperado = loginControllerImp.loginUser("FirstUser", "1234");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.OK));

        userDTO.setUsername("UserSecond");

        when(loginServiceImp.loginUser("UserSecond", "9876")).thenReturn(Optional.ofNullable(userDTO));

        resultado = loginServiceImp.loginUser("UserSecond", "9876");

        esperado = loginControllerImp.loginUser("UserSecond", "9876");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.OK));


        userDTO.setUsername("User3");

        when(loginServiceImp.loginUser("User3", "bestUser123")).thenReturn(Optional.ofNullable(userDTO));

        resultado = loginServiceImp.loginUser("User3", "bestUser123");

        esperado = loginControllerImp.loginUser("User3", "bestUser123");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.OK));

    }

    @Test
    void InvalidloginUserTest() {

        userDTO.setUsername("FirstUserñ");

        when(loginServiceImp.loginUser("FirstUserñ", "1234")).thenReturn(Optional.empty());

        Optional<UserDTO> resultado = loginServiceImp.loginUser("FirstUserñ", "1234");

        ResponseEntity<UserPassDTO> esperado = loginControllerImp.loginUser("FirstUserñ", "1234");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.NOT_FOUND));


        userDTO.setUsername("UserSecond");

        when(loginServiceImp.loginUser("UserSecond", "9876")).thenReturn(Optional.empty());

        resultado = loginServiceImp.loginUser("UserSecond", "9876");

        esperado = loginControllerImp.loginUser("UserSecond", "9876");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.NOT_FOUND));


        userDTO.setUsername("User3");

        when(loginServiceImp.loginUser("User3", "bestUser123")).thenReturn(Optional.empty());

        resultado = loginServiceImp.loginUser("User3", "bestUser123");

        esperado = loginControllerImp.loginUser("User3", "bestUser123");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.NOT_FOUND));
    }

}
