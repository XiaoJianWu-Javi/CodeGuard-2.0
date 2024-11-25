package es.tfg.codeguard.controller;

import es.tfg.codeguard.controller.imp.RegisterControllerImp;
import es.tfg.codeguard.model.dto.AuthDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.service.RegisterService;
import es.tfg.codeguard.util.UsernameNotValidException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
class RegisterControllerTest {

    @Mock
    private RegisterService registerService;

    @InjectMocks
    private RegisterControllerImp registerControllerImp;

    private UserPassDTO userPassDTO;
    private AuthDTO jsonParserDTO;

    @Test
    void registerUserTest() {

        jsonParserDTO = new AuthDTO("FirstUser", "1234");

        userPassDTO = new UserPassDTO("FirstUser", false);

        when(registerService.registerUser(jsonParserDTO)).thenReturn(userPassDTO);

        UserPassDTO resultado = registerService.registerUser(jsonParserDTO);

        ResponseEntity<UserPassDTO> esperado = registerControllerImp.registerUser(jsonParserDTO);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.CREATED));


        jsonParserDTO = new AuthDTO("UserSecond", "9876");

        userPassDTO = new UserPassDTO("UserSecond", false);

        when(registerService.registerUser(jsonParserDTO)).thenReturn(userPassDTO);

        resultado = registerService.registerUser(jsonParserDTO);

        esperado = registerControllerImp.registerUser(jsonParserDTO);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.CREATED));


        jsonParserDTO = new AuthDTO("User3", "bestUser123");

        userPassDTO = new UserPassDTO("User3", false);

        when(registerService.registerUser(jsonParserDTO)).thenReturn(userPassDTO);

        resultado = registerService.registerUser(jsonParserDTO);

        esperado = registerControllerImp.registerUser(jsonParserDTO);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.CREATED));

    }

    //TODO: TEST CONTRASEÑA VACIA O NULA Y NOMBRE YA USADO
    @Test
    void invalidRegisterUserTest() {


        jsonParserDTO = new AuthDTO("FirstUserñ", "1234");

        when(registerService.registerUser(jsonParserDTO)).thenThrow(UsernameNotValidException.class);

        ResponseEntity<UserPassDTO> esperado = registerControllerImp.registerUser(jsonParserDTO);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.BAD_REQUEST));


        jsonParserDTO = new AuthDTO("FirstUser", "1234");

        when(registerService.registerUser(new AuthDTO("FirstUser", "1234"))).thenThrow(UsernameNotValidException.class);

        esperado = registerControllerImp.registerUser(jsonParserDTO);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.BAD_REQUEST));


        jsonParserDTO = new AuthDTO("User3;;:.+", "bestUser123");

        when(registerService.registerUser(new AuthDTO("User3;;:.+", "bestUser123"))).thenThrow(UsernameNotValidException.class);

        esperado = registerControllerImp.registerUser(jsonParserDTO);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

    }


}
