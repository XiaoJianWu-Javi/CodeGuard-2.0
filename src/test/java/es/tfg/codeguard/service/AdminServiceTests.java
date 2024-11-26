package es.tfg.codeguard.service;

import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    void TestFailAdminServiceDeleteMethod() {

        when(userRepository.findById("i2udgiqdqi????!=¿!02'31")).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> adminServiceImp.deleteUser("i2udgiqdqi????!=¿!02'31"));

    }

    @Test
    void TestFineAdminServiceDeleteMethod() {
        Optional<UserPass> userPassOpt = Optional.of(userPass);
        //when(userPassRepository.findById("Gandalf")).thenReturn(userPassOpt);

        Optional<User> userOpt = Optional.of(user);
        when(userRepository.findById("Gandalf")).thenReturn(userOpt);

        UserDTO deleteUser = adminServiceImp.deleteUser("Gandalf");

        DeletedUser deletedUserExpected = deletedUser;

        assertThat(deletedUserExpected).usingRecursiveComparison().isEqualTo(deleteUser);

    }

    @Test
    void TestFailAdminServiceUpdateMethod() {
        when(userRepository.findById("Gandalf")).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> adminServiceImp.updateUser("Gandalf", "cantpass"));

    }

    @Test
    void TestFineAdminServiceUpdateMethod() {

        UserPassDTO userExpected = new UserPassDTO("Gandalf", false);

        when(userRepository.findById("Gandalf")).thenReturn(Optional.of(user));
        when(userPassRepository.findById("Gandalf")).thenReturn(Optional.of(userPass));

        UserPassDTO userOptional = adminServiceImp.updateUser("Gandalf", "cantpass");

        assertThat(userExpected).usingRecursiveComparison().isEqualTo(userOptional);
    }

    @Test
    void TestFailAdminServiceGrantTesterMethod() {

        when(userRepository.findById("Gandalf")).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> adminServiceImp.grantTester("Gandalf"));

    }

    @Test
    void TestFineAdminServiceGrantTesterMethod() {

        when(userRepository.findById("Gandalf")).thenReturn(Optional.of(user));
        user.setTester(true);
        UserDTO userExpected = new UserDTO(user);
        UserDTO user = adminServiceImp.grantTester("Gandalf");

        assertThat(userExpected).usingRecursiveComparison().isEqualTo(user);

    }

    @Test
    void TestFailAdminServiceRevokeTesterMethod() {
        when(userRepository.findById("Gandalf")).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> adminServiceImp.revokeTester("Gandalf"));

    }

    @Test
    void TestFineElderServiceRevokeTesterMethod() {
        user.setTester(true);
        when(userRepository.findById("Gandalf")).thenReturn(Optional.of(user));

        user.setTester(false);

        UserDTO userExpected = new UserDTO(user);

        UserDTO user = adminServiceImp.revokeTester("Gandalf");

        assertThat(userExpected).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void TestFailElderServiceGrantCreatorMethod() {
        when(userRepository.findById("Gandalf")).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> adminServiceImp.grantCreator("Gandalf"));

    }

    @Test
    void TestFineUserServiceGrantCreatorMethod() {
        when(userRepository.findById("Gandalf")).thenReturn(Optional.of(user));

        user.setCreator(true);

        UserDTO userExpected = new UserDTO(user);


        UserDTO user = adminServiceImp.grantCreator("Gandalf");

        assertThat(userExpected).usingRecursiveComparison().isEqualTo(user);

    }

    @Test
    void TestFailElderServiceRevokeCreatorMethod() {
        when(userRepository.findById("Gandalf")).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> adminServiceImp.revokeCreator("Gandalf"));

    }

    @Test
    void TestFineUserServiceRevokeCreatorMethod() {
        user.setCreator(true);
        when(userRepository.findById("Gandalf")).thenReturn(Optional.of(user));

        user.setCreator(false);

        UserDTO userExpected = new UserDTO(user);

        UserDTO user = adminServiceImp.revokeCreator("Gandalf");

        assertThat(userExpected).usingRecursiveComparison().isEqualTo(user);

    }

    @Test
    void TestFailElderServiceGrantAllPrivilegesMethod() {
        when(userRepository.findById("Gandalf")).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> adminServiceImp.grantAllPrivileges("Gandalf"));

    }

    @Test
    void TestFineAdminServiceGrantAllPrivilegesMethod() {
        when(userRepository.findById("Gandalf")).thenReturn(Optional.of(user));

        user.setCreator(true);
        user.setTester(true);

        UserDTO userExpected = new UserDTO(user);

        UserDTO user = adminServiceImp.grantAllPrivileges("Gandalf");

        assertThat(userExpected).usingRecursiveComparison().isEqualTo(user);

    }

    @Test
    void TestFailElderServiceRevokeAllPrivilegesMethod() {
        when(userRepository.findById("Gandalf")).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> adminServiceImp.revokeAllPrivileges("Gandalf"));

    }

    @Test
    void TestFineUserServiceRevokeAllPrivilegesMethod() {
        user.setCreator(true);
        user.setTester(true);
        when(userRepository.findById("Gandalf")).thenReturn(Optional.of(user));
        user.setCreator(false);
        user.setTester(false);

        UserDTO userExpected = new UserDTO(user);

        UserDTO user = adminServiceImp.revokeAllPrivileges("Gandalf");

        assertThat(userExpected).usingRecursiveComparison().isEqualTo(user);

    }

}
