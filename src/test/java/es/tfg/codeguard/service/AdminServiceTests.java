package es.tfg.codeguard.service;

import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.model.dto.UserPrivilegesDTO;
import es.tfg.codeguard.model.entity.deleteduser.DeletedUser;
import es.tfg.codeguard.model.entity.user.User;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.model.repository.deleteduser.DeletedUserRepository;
import es.tfg.codeguard.model.repository.user.UserRepository;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.service.imp.AdminServiceImp;
import es.tfg.codeguard.util.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AdminServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserPassRepository userPassRepository;

    @Mock
    private DeletedUserRepository deletedUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminServiceImp adminServiceImp;

    private UserPass userPass;

    private User user;

    private DeletedUser deletedUser;

    @BeforeEach
    void setUp() {

        when(passwordEncoder.encode("cantpass")).thenReturn(new BCryptPasswordEncoder().encode("cantpass"));

        userPass = new UserPass();
        userPass.setUsername("Gandalf");
        userPass.setAdmin(false);
        userPass.setHashedPass(passwordEncoder.encode("cantpass"));

        user = new User("Gandalf");

        deletedUser = new DeletedUser(user);
    }

    @Test
    void adminDeleteUserTestUserNotFound() {

        when(userRepository.findById("i2udgiqdqi????!=¿!02'31")).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> adminServiceImp.deleteUser("i2udgiqdqi????!=¿!02'31"));

    }

    @Test
    void adminDeleteUserTest() {
        Optional<UserPass> userPassOpt = Optional.of(userPass);

        Optional<User> userOpt = Optional.of(user);
        when(userRepository.findById("Gandalf")).thenReturn(userOpt);

        UserDTO resultDeleteUser = adminServiceImp.deleteUser("Gandalf");

        DeletedUser expectedDeleteUser = deletedUser;

        assertThat(expectedDeleteUser).usingRecursiveComparison().isEqualTo(resultDeleteUser);

    }

    @ParameterizedTest
    @CsvSource({
            "G4ndalf,cantpass",
            "D3xter,newSecurePassword12",
            "P0l,9876secure"
    })
    void adminChangeUserPasswordTestUserNotFound(String username, String newPassword) {
        when(userRepository.findById(username)).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> adminServiceImp.updatePassword(username, newPassword));

    }

    @Test
    void adminChangeUserPasswordTest() {

        UserPassDTO userExpected = new UserPassDTO("Gandalf", false);

        when(userRepository.findById("Gandalf")).thenReturn(Optional.of(user));
        when(userPassRepository.findById("Gandalf")).thenReturn(Optional.of(userPass));

        UserPassDTO userOptional = adminServiceImp.updatePassword("Gandalf", "cantpass");

        assertThat(userExpected).usingRecursiveComparison().isEqualTo(userOptional);
    }

    @Test
    void adminUpdateUserPrivilegesTest() {

        UserPrivilegesDTO userPrivilegesDTO1 = new UserPrivilegesDTO("theBestMagician", false, true);

        UserDTO expectedUser = new UserDTO(userPrivilegesDTO1.username(), userPrivilegesDTO1.tester(), userPrivilegesDTO1.creator(), List.of());

        when(userRepository.findById(userPrivilegesDTO1.username())).thenReturn(Optional.of(new User(userPrivilegesDTO1.username(), false, false)));

        UserDTO resultUser = adminServiceImp.updateUserPrivileges(userPrivilegesDTO1);

        assertThat(expectedUser).usingRecursiveComparison().isEqualTo(resultUser);


        UserPrivilegesDTO userPrivilegesDTO2 = new UserPrivilegesDTO("MMM4gic", true, false);

        expectedUser = new UserDTO(userPrivilegesDTO2.username(), userPrivilegesDTO2.tester(), userPrivilegesDTO2.creator(), List.of());

        when(userRepository.findById(userPrivilegesDTO2.username())).thenReturn(Optional.of(new User(userPrivilegesDTO2.username(), false, false)));

        resultUser = adminServiceImp.updateUserPrivileges(userPrivilegesDTO2);

        assertThat(expectedUser).usingRecursiveComparison().isEqualTo(resultUser);


        UserPrivilegesDTO userPrivilegesDTO3 = new UserPrivilegesDTO("SSS0ulD3v", true, true);

        expectedUser = new UserDTO(userPrivilegesDTO3.username(), userPrivilegesDTO3.tester(), userPrivilegesDTO3.creator(), List.of());

        when(userRepository.findById(userPrivilegesDTO3.username())).thenReturn(Optional.of(new User(userPrivilegesDTO3.username(), false, false)));

        resultUser = adminServiceImp.updateUserPrivileges(userPrivilegesDTO3);

        assertThat(expectedUser).usingRecursiveComparison().isEqualTo(resultUser);


        UserPrivilegesDTO userPrivilegesDTO4 = new UserPrivilegesDTO("Noo0bDev", false, false);

        expectedUser = new UserDTO(userPrivilegesDTO4.username(), userPrivilegesDTO4.tester(), userPrivilegesDTO4.creator(), List.of());

        when(userRepository.findById(userPrivilegesDTO4.username())).thenReturn(Optional.of(new User(userPrivilegesDTO4.username(), false, false)));

        resultUser = adminServiceImp.updateUserPrivileges(userPrivilegesDTO4);

        assertThat(expectedUser).usingRecursiveComparison().isEqualTo(resultUser);

    }

    @ParameterizedTest
    @ValueSource(strings = {"43ero", "t3rreta", "+´`2marck", "º-º"})
    void adminUpdateUserPrivilegesTest(String username) {

        UserPrivilegesDTO userPrivilegesDTO1 = new UserPrivilegesDTO("username", true, true);

        when(userRepository.findById(userPrivilegesDTO1.username())).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> adminServiceImp.updateUserPrivileges(userPrivilegesDTO1));

    }

}
