// package es.tfg.codeguard.controller;

// import es.tfg.codeguard.controller.imp.RegisterControllerImp;
// import es.tfg.codeguard.model.dto.UserPassDTO;
// import es.tfg.codeguard.service.UserService;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;

// import java.util.Optional;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.mockito.Mockito.when;

// @ExtendWith(MockitoExtension.class)
// @SpringBootTest
// public class RegisterControllerTest {

//     @Mock
//     private UserService userService;

//     @InjectMocks
//     private RegisterControllerImp registerControllerImp;

//     private UserPassDTO userPassDTO;

//     @BeforeEach
//     void setup() {
//         userPassDTO = new UserPassDTO();
//         userPassDTO.setAdmin(false);

//     }

//     @Test
//     void registerUserTest() {

//         userPassDTO.setUsername("FirstUser");

//         when(userService.registerUser("FirstUser", "1234")).thenReturn(Optional.of(userPassDTO));

//         Optional<UserPassDTO> resultado = userService.registerUser("FirstUser", "1234");

//         ResponseEntity<UserPassDTO> esperado = registerControllerImp.registerUser("FirstUser", "1234");

//         assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.CREATED));


//         userPassDTO.setUsername("UserSecond");

//         when(userService.registerUser("UserSecond", "9876")).thenReturn(Optional.ofNullable(userPassDTO));

//         resultado = userService.registerUser("UserSecond", "9876");

//         esperado = registerControllerImp.registerUser("UserSecond", "9876");

//         assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.CREATED));


//         userPassDTO.setUsername("User3");

//         when(userService.registerUser("User3", "bestUser123")).thenReturn(Optional.ofNullable(userPassDTO));

//         resultado = userService.registerUser("User3", "bestUser123");

//         esperado = registerControllerImp.registerUser("User3", "bestUser123");

//         assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.CREATED));

//     }

//     @Test
//     void invalidRegisterUserTest() {

//         when(userService.registerUser("FirstUserñ", "1234")).thenReturn(Optional.empty());

//         ResponseEntity<UserPassDTO> esperado = registerControllerImp.registerUser("FirstUserñ", "1234");

//         assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.BAD_REQUEST));


//         when(userService.registerUser("UserSecond<?php>", "9876")).thenReturn(Optional.empty());

//         esperado = registerControllerImp.registerUser("UserSecond<?php>", "9876");

//         assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.BAD_REQUEST));


//         when(userService.registerUser("User3;;:.+", "bestUser123")).thenReturn(Optional.empty());

//         esperado = registerControllerImp.registerUser("User3;;:.+", "bestUser123");

//         assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

//     }

// }
