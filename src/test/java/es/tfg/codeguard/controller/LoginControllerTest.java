package es.tfg.codeguard.controller;

import es.tfg.codeguard.controller.imp.LoginControllerImp;
import es.tfg.codeguard.model.dto.AuthDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.service.JWTService;
import es.tfg.codeguard.service.LoginService;
import es.tfg.codeguard.util.IncorrectPasswordException;
import es.tfg.codeguard.util.UserNotFoundException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {

    @Mock
    private LoginService loginService;

    @Mock
    private JWTService jwtService;

    @InjectMocks
    private LoginControllerImp loginControllerImp;

    @ParameterizedTest
    @ValueSource(strings = {"Rachel", "Dext3r", "Houdini", "rubi"})
    void loginUserTest(String username) {

        AuthDTO authDTO = new AuthDTO(username, "password1234");

        UserPassDTO expectedUserPassDTO = new UserPassDTO(username, false);

        when(loginService.loginUser(authDTO)).thenReturn(expectedUserPassDTO);
        when(jwtService.createJwt(expectedUserPassDTO)).thenReturn("TokenDe" + username);

        ResponseEntity<UserPassDTO> result = loginControllerImp.loginUser(authDTO);

        HttpHeaders expectedHeaders = new HttpHeaders();
        expectedHeaders.set("Authorization", jwtService.createJwt(expectedUserPassDTO));

        ResponseEntity<UserPassDTO> expected = ResponseEntity.ok().headers(expectedHeaders).body(expectedUserPassDTO);

        assertThat(expected).usingRecursiveComparison().isEqualTo(result);

    }

    @ParameterizedTest
    @ValueSource(strings = {"Rachel", "Dext3r", "Houdini", "rubi"})
    void loginUserTestUserNotFound(String username) {

        AuthDTO authDTO = new AuthDTO(username, "password1234");

        UserPassDTO expectedUserPassDTO = new UserPassDTO(username, false);

        when(loginService.loginUser(authDTO)).thenThrow(UserNotFoundException.class);

        ResponseEntity<UserPassDTO> result = loginControllerImp.loginUser(authDTO);

        assertThat(new ResponseEntity<>(HttpStatus.NOT_FOUND)).usingRecursiveComparison().isEqualTo(result);

    }

    @ParameterizedTest
    @ValueSource(strings = {"Rachel", "Dext3r", "Houdini", "rubi"})
    void loginUserTestIncorrectPassword(String username) {

        AuthDTO authDTO = new AuthDTO(username, "1234password");

        UserPassDTO expectedUserPassDTO = new UserPassDTO(username, false);

        when(loginService.loginUser(authDTO)).thenThrow(IncorrectPasswordException.class);

        ResponseEntity<UserPassDTO> result = loginControllerImp.loginUser(authDTO);

        assertThat(new ResponseEntity<>(HttpStatus.BAD_REQUEST)).usingRecursiveComparison().isEqualTo(result);

    }

}