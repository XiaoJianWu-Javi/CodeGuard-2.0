package es.tfg.codeguard.controller;

import es.tfg.codeguard.controller.imp.RegisterControllerImp;
import es.tfg.codeguard.model.dto.JsonParserUserPassDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.service.RegisterService;
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
public class RegisterControllerTest {

    @Mock
    private RegisterService registerService;

    @InjectMocks
    private RegisterControllerImp registerControllerImp;

    private UserPassDTO userPassDTO;
    private JsonParserUserPassDTO jsonParserDTO;

    @BeforeEach
    void setup() {
        userPassDTO = new UserPassDTO();
        userPassDTO.setAdmin(false);

        jsonParserDTO=new JsonParserUserPassDTO();

    }

    @Test
    void registerUserTest() {

        jsonParserDTO.setUsername("FirstUser");
        jsonParserDTO.setPassword("1234");

        userPassDTO.setUsername("FirstUser");

        when(registerService.registerUser(jsonParserDTO)).thenReturn(Optional.of(userPassDTO));

        Optional<UserPassDTO> resultado = registerService.registerUser(jsonParserDTO);

        ResponseEntity<UserPassDTO> esperado = registerControllerImp.registerUser(jsonParserDTO);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.CREATED));


        jsonParserDTO.setUsername("UserSecond");
        jsonParserDTO.setPassword("9876");

        userPassDTO.setUsername("UserSecond");

        when(registerService.registerUser(jsonParserDTO)).thenReturn(Optional.ofNullable(userPassDTO));

        resultado = registerService.registerUser(jsonParserDTO);

        esperado = registerControllerImp.registerUser(jsonParserDTO);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.CREATED));


        jsonParserDTO.setUsername("User3");
        jsonParserDTO.setPassword("bestUser123");

        userPassDTO.setUsername("User3");

        when(registerService.registerUser(jsonParserDTO)).thenReturn(Optional.ofNullable(userPassDTO));

        resultado = registerService.registerUser(jsonParserDTO);

        esperado = registerControllerImp.registerUser(jsonParserDTO);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.CREATED));

    }

    @Test
    void invalidRegisterUserTest() {

        jsonParserDTO.setUsername("FirstUserñ");
        jsonParserDTO.setPassword("1234");

        when(registerService.registerUser(jsonParserDTO)).thenReturn(Optional.empty());

        ResponseEntity<UserPassDTO> esperado = registerControllerImp.registerUser(jsonParserDTO);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.BAD_REQUEST));


        jsonParserDTO.setUsername("FirstUserñ");
        jsonParserDTO.setPassword("1234");

        when(registerService.registerUser(jsonParserDTO)).thenReturn(Optional.empty());

        esperado = registerControllerImp.registerUser(jsonParserDTO);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.BAD_REQUEST));


        jsonParserDTO.setUsername("User3;;:.+");
        jsonParserDTO.setPassword("bestUser123");

        when(registerService.registerUser(jsonParserDTO)).thenReturn(Optional.empty());

        esperado = registerControllerImp.registerUser(jsonParserDTO);

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

    }

}
