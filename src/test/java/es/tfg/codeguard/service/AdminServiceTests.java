package es.tfg.codeguard.service;

import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.model.entity.deleteduser.DeletedUser;
import es.tfg.codeguard.model.entity.user.User;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.model.repository.deleteduser.DeletedUserRepository;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.model.repository.user.UserRepository;
import es.tfg.codeguard.service.imp.AdminServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
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
        when(userPassRepository.findById("Gandalf")).thenReturn(Optional.empty());

        Optional<UserDTO> deleteUser = adminServiceImp.deleteUser("Gandalf");
        assertThat(deleteUser).isEmpty();
    }

    @Test
    void TestFineAdminServiceDeleteMethod() {
        Optional<UserPass> userPassOpt = Optional.of(userPass);
        when(userPassRepository.findById("Gandalf")).thenReturn(userPassOpt);

        Optional<User> userOpt = Optional.of(user);
        when(userRepository.findById("Gandalf")).thenReturn(userOpt);

        Optional<UserDTO> deleteUser = adminServiceImp.deleteUser("Gandalf");

        Optional<DeletedUser> deletedUserExpected = Optional.of(deletedUser);

        assertThat(deletedUserExpected).usingRecursiveComparison().isEqualTo(deleteUser);

    }

    @Test
    void TestFailAdminServiceUpdateMethod() {
        when(userPassRepository.findById("Gandalf")).thenReturn(Optional.empty());
        Optional<UserPassDTO> userOptional = adminServiceImp.updateUser("Gandalf", "cantpass");

        assertThat(userOptional).isEmpty();

    }

    @Test
    void TestFineAdminServiceUpdateMethod() {

        when(userPassRepository.findById("Gandalf")).thenReturn(Optional.of(userPass));

        UserPassDTO userPassDTO = new UserPassDTO("Gandalf", false);

        Optional<UserPassDTO> userExpected = Optional.of(userPassDTO);
        Optional<UserPassDTO> userOptional = adminServiceImp.updateUser("Gandalf", "cantpass");

        assertThat(userExpected).usingRecursiveComparison().isEqualTo(userOptional);

    }

    @Test
    void TestFailAdminServiceGrantTesterMethod() {
        when(userRepository.findById("Gandalf")).thenReturn(Optional.empty());
        Optional<UserDTO> userOptional = adminServiceImp.grantTester("Gandalf");

        assertThat(userOptional).isEmpty();

    }

    @Test
    void TestFineAdminServiceGrantTesterMethod() {

        when(userRepository.findById("Gandalf")).thenReturn(Optional.of(user));
        user.setTester(true);
        UserDTO userDTO = new UserDTO(user);
        Optional<UserDTO> userExpected = Optional.of(userDTO);
        Optional<UserDTO> userOptional = adminServiceImp.grantTester("Gandalf");

        assertThat(userExpected).usingRecursiveComparison().isEqualTo(userOptional);

    }

    @Test
    void TestFailAdminServiceRevokeTesterMethod() {
        when(userRepository.findById("Gandalf")).thenReturn(Optional.empty());
        Optional<UserDTO> userOptional = adminServiceImp.revokeTester("Gandalf");

        assertThat(userOptional).isEmpty();

    }

    @Test
    void TestFineElderServiceRevokeTesterMethod() {
        user.setTester(true);
        when(userRepository.findById("Gandalf")).thenReturn(Optional.of(user));

        user.setTester(false);

        UserDTO userDTO = new UserDTO(user);

        Optional<UserDTO> userExpected = Optional.of(userDTO);

        Optional<UserDTO> userOptional = adminServiceImp.revokeTester("Gandalf");

        assertThat(userExpected).usingRecursiveComparison().isEqualTo(userOptional);
    }

    @Test
    void TestFailElderServiceGrantCreatorMethod() {
        when(userRepository.findById("Gandalf")).thenReturn(Optional.empty());
        Optional<UserDTO> userOptional = adminServiceImp.grantCreator("Gandalf");

        assertThat(userOptional).isEmpty();

    }

    @Test
    void TestFineUserServiceGrantCreatorMethod() {
        when(userRepository.findById("Gandalf")).thenReturn(Optional.of(user));

        user.setCreator(true);

        UserDTO userDTO = new UserDTO(user);

        Optional<UserDTO> userExpected = Optional.of(userDTO);
        Optional<UserDTO> userOptional = adminServiceImp.grantCreator("Gandalf");

        assertThat(userExpected).usingRecursiveComparison().isEqualTo(userOptional);

    }

    @Test
    void TestFailElderServiceRevokeCreatorMethod() {
        when(userRepository.findById("Gandalf")).thenReturn(Optional.empty());
        Optional<UserDTO> userOptional = adminServiceImp.revokeCreator("Gandalf");

        assertThat(userOptional).isEmpty();

    }

    @Test
    void TestFineUserServiceRevokeCreatorMethod() {
        user.setCreator(true);
        when(userRepository.findById("Gandalf")).thenReturn(Optional.of(user));

        user.setCreator(false);

        UserDTO userDTO = new UserDTO(user);

        Optional<UserDTO> userExpected = Optional.of(userDTO);
        Optional<UserDTO> userOptional = adminServiceImp.revokeCreator("Gandalf");

        assertThat(userExpected).usingRecursiveComparison().isEqualTo(userOptional);

    }

    @Test
    void TestFailElderServiceGrantAllPrivilegesMethod() {
        when(userRepository.findById("Gandalf")).thenReturn(Optional.empty());
        Optional<UserDTO> userOptional = adminServiceImp.grantAllPrivileges("Gandalf");

        assertThat(userOptional).isEmpty();

    }

    @Test
    void TestFineAdminServiceGrantAllPrivilegesMethod() {
        when(userRepository.findById("Gandalf")).thenReturn(Optional.of(user));

        user.setCreator(true);
        user.setTester(true);

        UserDTO userDTO = new UserDTO(user);

        Optional<UserDTO> userExpected = Optional.of(userDTO);
        Optional<UserDTO> userOptional = adminServiceImp.grantAllPrivileges("Gandalf");

        assertThat(userExpected).usingRecursiveComparison().isEqualTo(userOptional);

    }

    @Test
    void TestFailElderServiceRevokeAllPrivilegesMethod() {
        when(userRepository.findById("Gandalf")).thenReturn(Optional.empty());
        Optional<UserDTO> userOptional = adminServiceImp.revokeAllPrivileges("Gandalf");

        assertThat(userOptional).isEmpty();

    }

    @Test
    void TestFineUserServiceRevokeAllPrivilegesMethod() {
        user.setCreator(true);
        user.setTester(true);
        when(userRepository.findById("Gandalf")).thenReturn(Optional.of(user));
        user.setCreator(false);
        user.setTester(false);

        UserDTO userDTO = new UserDTO(user);

        Optional<UserDTO> userExpected = Optional.of(userDTO);
        Optional<UserDTO> userOptional = adminServiceImp.revokeAllPrivileges("Gandalf");

        assertThat(userExpected).usingRecursiveComparison().isEqualTo(userOptional);

    }

}
