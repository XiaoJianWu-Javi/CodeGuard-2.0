package es.tfg.codeguard.controller;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.tfg.codeguard.controller.imp.RegisterControllerImp;
import es.tfg.codeguard.model.dto.AuthDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.service.RegisterService;

import java.util.Optional;

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

        when(registerService.registerUser(jsonParserDTO)).thenReturn(Optional.of(userPassDTO));

        Optional<UserPassDTO> resultado = registerService.registerUser(jsonParserDTO);

        ResponseEntity<UserPassDTO> esperado = registerControllerImp.registerUser(jsonParserDTO);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.CREATED));


        jsonParserDTO = new AuthDTO("UserSecond", "9876");
        
        userPassDTO = new UserPassDTO("UserSecond", false);

        when(registerService.registerUser(jsonParserDTO)).thenReturn(Optional.ofNullable(userPassDTO));

        resultado = registerService.registerUser(jsonParserDTO);

        esperado = registerControllerImp.registerUser(jsonParserDTO);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.CREATED));


        jsonParserDTO = new AuthDTO("User3", "bestUser123");
        
        userPassDTO = new UserPassDTO("User3", false);

        when(registerService.registerUser(jsonParserDTO)).thenReturn(Optional.ofNullable(userPassDTO));

        resultado = registerService.registerUser(jsonParserDTO);

        esperado = registerControllerImp.registerUser(jsonParserDTO);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.CREATED));

    }

    @Test
    void invalidRegisterUserTest() {


        jsonParserDTO = new AuthDTO("FirstUserñ", "1234");

        when(registerService.registerUser(jsonParserDTO)).thenReturn(Optional.empty());

        ResponseEntity<UserPassDTO> esperado = registerControllerImp.registerUser(jsonParserDTO);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.CONFLICT));


        jsonParserDTO = new AuthDTO("FirstUserñ", "1234");

        when(registerService.registerUser(jsonParserDTO)).thenReturn(Optional.empty());

        esperado = registerControllerImp.registerUser(jsonParserDTO);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.CONFLICT));


        jsonParserDTO = new AuthDTO("User3;;:.+", "bestUser123");

        when(registerService.registerUser(jsonParserDTO)).thenReturn(Optional.empty());

        esperado = registerControllerImp.registerUser(jsonParserDTO);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.CONFLICT));

    }

}
