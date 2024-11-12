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

    @Test
    void UpdateUserPasswordTest() {

        userPassDTO.setUsername("FirstUser");

        when(adminService.updateUser("FirstUser", "1234new")).thenReturn(Optional.ofNullable(userPassDTO));

        Optional<UserPassDTO> resultado = adminService.updateUser("FirstUser", "1234new");

        ResponseEntity<UserPassDTO> esperado = adminControllerImp.updateUser("FirstUser", "1234new");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.OK));


        userPassDTO.setUsername("SecondUser");

        when(adminService.updateUser("SecondUser", "9876new")).thenReturn(Optional.ofNullable(userPassDTO));

        resultado = adminService.updateUser("SecondUser", "9876new");

        esperado = adminControllerImp.updateUser("SecondUser", "9876new");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.OK));


        userPassDTO.setUsername("ThirdtUser");

        when(adminService.updateUser("ThirdtUser", "newpass")).thenReturn(Optional.ofNullable(userPassDTO));

        resultado = adminService.updateUser("ThirdtUser", "newpass");

        esperado = adminControllerImp.updateUser("ThirdtUser", "newpass");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.OK));


        userPassDTO.setUsername("FourthUser");

        when(adminService.updateUser("FourthUser", "newhola1234")).thenReturn(Optional.ofNullable(userPassDTO));

        resultado = adminService.updateUser("FourthUser", "newhola1234");

        esperado = adminControllerImp.updateUser("FourthUser", "newhola1234");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado.get(), HttpStatus.OK));

    }

    @Test
    void InvalidUpdateUserPasswordTest() {

        when(adminService.updateUser("FirstUser", "new1234;;")).thenReturn(Optional.empty());

        Optional<UserPassDTO> resultado = adminService.updateUser("FirstUser", "new1234;;");

        ResponseEntity<UserPassDTO> esperado = adminControllerImp.updateUser("FirstUser", "new1234;;");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.NOT_MODIFIED));


        when(adminService.updateUser("SecondUser", "newhola1234ºº")).thenReturn(Optional.empty());

        resultado = adminService.updateUser("SecondUser", "newhola1234ºº");

        esperado = adminControllerImp.updateUser("SecondUser", "newhola1234ºº");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.NOT_MODIFIED));


        when(adminService.updateUser("ThirdUser", "ªªaaaa")).thenReturn(Optional.empty());

        resultado = adminService.updateUser("ThirdUser", "ªªaaa");

        esperado = adminControllerImp.updateUser("ThirdUser", "ªªaaaa");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.NOT_MODIFIED));


        when(adminService.updateUser("FourthUser", "juitenDiten,,,,")).thenReturn(Optional.empty());

        resultado = adminService.updateUser("FourthUser", "juitenDiten,,,,");

        esperado = adminControllerImp.updateUser("FourthUser", "juitenDiten,,,,");

        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(HttpStatus.NOT_MODIFIED));

    }

//TODO: Incremento de código Strint 2

//    @Test
//    void GetAllUsersTest() {
//
//        UserDTO user1 = new UserDTO();
//        user1.setUsername("user1");
//        user1.setCreator(false);
//        user1.setTester(false);
//
//        UserDTO user2 = new UserDTO();
//        user2.setUsername("user2");
//        user2.setCreator(false);
//        user2.setTester(false);
//
//        UserDTO user3 = new UserDTO();
//        user3.setUsername("user3");
//        user3.setCreator(false);
//        user3.setTester(false);
//
//        when(userService.getAllUsers()).thenReturn(new ArrayList<UserDTO>(Arrays.asList()));
//
//        List<UserDTO> resultado = userService.getAllUsers();
//
//        ResponseEntity<List<UserDTO>> esperado = userControllerImp.getAllUser();
//
//        Assertions.assertEquals(0, resultado.size());
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.OK));
//
//
//        when(userService.getAllUsers()).thenReturn(new ArrayList<UserDTO>(Arrays.asList(user1)));
//
//        resultado = userService.getAllUsers();
//
//        esperado = userControllerImp.getAllUser();
//
//        Assertions.assertEquals(1, resultado.size());
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.OK));
//
//
//        when(userService.getAllUsers()).thenReturn(new ArrayList<UserDTO>(Arrays.asList(user1, user2)));
//
//        resultado = userService.getAllUsers();
//
//        esperado = userControllerImp.getAllUser();
//
//        Assertions.assertEquals(2, resultado.size());
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.OK));
//
//
//        when(userService.getAllUsers()).thenReturn(new ArrayList<UserDTO>(Arrays.asList(user1, user2, user3)));
//
//        resultado = userService.getAllUsers();
//
//        esperado = userControllerImp.getAllUser();
//
//        Assertions.assertEquals(3, resultado.size());
//        assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.OK));
//
//    }

}
