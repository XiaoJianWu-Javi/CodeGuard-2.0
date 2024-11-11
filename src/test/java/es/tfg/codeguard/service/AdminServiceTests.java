package es.tfg.codeguard.service;

import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.model.entity.DeletedUser;
import es.tfg.codeguard.model.entity.User;
import es.tfg.codeguard.model.entity.UserPass;
import es.tfg.codeguard.model.repository.DeletedUserRepository;
import es.tfg.codeguard.model.repository.UserPassRepository;
import es.tfg.codeguard.model.repository.UserRepository;
import es.tfg.codeguard.service.imp.AdminServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

    @InjectMocks
    private AdminServiceImp adminServiceImp;

    private UserPass userPass;

    private User user;

    private DeletedUser deletedUser;

    @BeforeEach
    public void setUp(){

        userPass = new UserPass();
        userPass.setUsername("Gandalf");
        userPass.setAdmin(false);
        userPass.setHashedPass(new BCryptPasswordEncoder().encode("cantpass"));

        user = new User();
        user.setUsername("Gandalf");


        deletedUser = new DeletedUser();
        deletedUser.setUsername("Gandalf");


    }


    @Test
    public void TestFailAdminServiceDeleteMethod(){
        when(userPassRepository.findById("Gandalf")).thenReturn(Optional.empty());

        Optional<UserDTO> deleteUser = adminServiceImp.deleteUser("Gandalf");
        assertThat(deleteUser).isEmpty();
    }

    @Test
    public void TestFineAdminServiceDeleteMethod(){
        Optional<UserPass> userPassOpt = Optional.of(userPass);
        when(userPassRepository.findById("Gandalf")).thenReturn(userPassOpt);


        Optional<User> userOpt = Optional.of(user);
        when(userRepository.findById("Gandalf")).thenReturn(userOpt);

        Optional<UserDTO> deleteUser = adminServiceImp.deleteUser("Gandalf");

        Optional<DeletedUser> deletedUserExpected = Optional.of(deletedUser);

        assertThat(deletedUserExpected).usingRecursiveComparison().isEqualTo(deleteUser);



    }

    @Test
    public void TestFailAdminServiceUpdateMethod() {
        when(userPassRepository.findById("Gandalf")).thenReturn(Optional.empty());
        Optional<UserPassDTO> user = adminServiceImp.updateUser("Gandalf", "cantpass");

        assertThat(user).isEmpty();

    }

    @Test
    public void TestFineAdminServiceUpdateMethod() {

        when(userPassRepository.findById("Gandalf")).thenReturn(Optional.of(userPass));


        UserPassDTO userPassDTO = new UserPassDTO();
        userPassDTO.setUsername("Gandalf");
        userPassDTO.setAdmin(false);
        Optional<UserPassDTO> userExpected = Optional.of(userPassDTO);
        Optional<UserPassDTO> user = adminServiceImp.updateUser("Gandalf", "cantpass");

        assertThat(userExpected).usingRecursiveComparison().isEqualTo(user);

    }


    @Test
    public void TestFailAdminServiceGrantTesterMethod() {
        when(userRepository.findById("Gandalf")).thenReturn(Optional.empty());
        Optional<UserDTO> user = adminServiceImp.grantTester("Gandalf");

        assertThat(user).isEmpty();

    }

    @Test
    public void TestFineAdminServiceGrantTesterMethod() {

        when(userRepository.findById("Gandalf")).thenReturn(Optional.of(user));
        user.setTester(true);
        UserDTO userDTO = new UserDTO(user);
        Optional<UserDTO> userExpected = Optional.of(userDTO);
        Optional<UserDTO> user = adminServiceImp.grantTester("Gandalf");

        assertThat(userExpected).usingRecursiveComparison().isEqualTo(user);

    }

    @Test
    public void TestFailAdminServiceRevokeTesterMethod() {
        when(userRepository.findById("Gandalf")).thenReturn(Optional.empty());
        Optional<UserDTO> user = adminServiceImp.revokeTester("Gandalf");

        assertThat(user).isEmpty();

    }

    @Test
    public void TestFineElderServiceRevokeTesterMethod() {
        user.setTester(true);
        when(userRepository.findById("Gandalf")).thenReturn(Optional.of(user));

        user.setTester(false);

        UserDTO userDTO = new UserDTO(user);

        Optional<UserDTO> userExpected = Optional.of(userDTO);



        Optional<UserDTO> user = adminServiceImp.revokeTester("Gandalf");

        assertThat(userExpected).usingRecursiveComparison().isEqualTo(user);

    }

    @Test
    public void TestFailElderServiceGrantCreatorMethod() {
        when(userRepository.findById("Gandalf")).thenReturn(Optional.empty());
        Optional<UserDTO> user = adminServiceImp.grantCreator("Gandalf");

        assertThat(user).isEmpty();

    }

    @Test
    public void TestFineUserServiceGrantCreatorMethod() {
        when(userRepository.findById("Gandalf")).thenReturn(Optional.of(user));

        user.setCreator(true);

        UserDTO userDTO = new UserDTO(user);

        Optional<UserDTO> userExpected = Optional.of(userDTO);
        Optional<UserDTO> user = adminServiceImp.grantCreator("Gandalf");

        assertThat(userExpected).usingRecursiveComparison().isEqualTo(user);

    }

    @Test
    public void TestFailElderServiceRevokeCreatorMethod() {
        when(userRepository.findById("Gandalf")).thenReturn(Optional.empty());
        Optional<UserDTO> user = adminServiceImp.revokeCreator("Gandalf");

        assertThat(user).isEmpty();

    }

    @Test
    public void TestFineUserServiceRevokeCreatorMethod() {
        user.setCreator(true);
        when(userRepository.findById("Gandalf")).thenReturn(Optional.of(user));

        user.setCreator(false);

        UserDTO userDTO = new UserDTO(user);

        Optional<UserDTO> userExpected = Optional.of(userDTO);
        Optional<UserDTO> user = adminServiceImp.revokeCreator("Gandalf");

        assertThat(userExpected).usingRecursiveComparison().isEqualTo(user);

    }


    @Test
    public void TestFailElderServiceGrantAllPrivilegesMethod() {
        when(userRepository.findById("Gandalf")).thenReturn(Optional.empty());
        Optional<UserDTO> user = adminServiceImp.grantAllPrivileges("Gandalf");

        assertThat(user).isEmpty();

    }

    @Test
    public void TestFineAdminServiceGrantAllPrivilegesMethod() {
        when(userRepository.findById("Gandalf")).thenReturn(Optional.of(user));

        user.setCreator(true);
        user.setTester(true);

        UserDTO userDTO = new UserDTO(user);

        Optional<UserDTO> userExpected = Optional.of( userDTO);
        Optional<UserDTO> user = adminServiceImp.grantAllPrivileges("Gandalf");

        assertThat(userExpected).usingRecursiveComparison().isEqualTo(user);

    }

    @Test
    public void TestFailElderServiceRevokeAllPrivilegesMethod() {
        when(userRepository.findById("Gandalf")).thenReturn(Optional.empty());
        Optional<UserDTO> user = adminServiceImp.revokeAllPrivileges("Gandalf");

        assertThat(user).isEmpty();

    }

    @Test
    public void TestFineUserServiceRevokeAllPrivilegesMethod() {
        user.setCreator(true);
        user.setTester(true);
        when(userRepository.findById("Gandalf")).thenReturn(Optional.of(user));
        user.setCreator(false);
        user.setTester(false);

        UserDTO userDTO = new UserDTO(user);

        Optional<UserDTO> userExpected = Optional.of( userDTO);
        Optional<UserDTO> user = adminServiceImp.revokeAllPrivileges("Gandalf");

        assertThat(userExpected).usingRecursiveComparison().isEqualTo(user);

    }

}
