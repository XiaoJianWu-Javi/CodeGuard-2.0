package es.tfg.codeguard.service;


import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.entity.deleteduser.DeletedUser;
import es.tfg.codeguard.model.entity.user.User;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.model.repository.deleteduser.DeletedUserRepository;
import es.tfg.codeguard.model.repository.user.UserRepository;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.service.imp.UserServiceImp;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserPassRepository userPassRepository;

    @Mock
    private DeletedUserRepository deletedUserRepository;

    @Mock
    private UserServiceImp userServiceImp;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTService jwtService;

    @Test
    public void TestFailUserServiceDeleteMethod() {
        when(userPassRepository.findById("Gandalf")).thenReturn(Optional.empty());

        Optional<UserDTO> deletedUser = userServiceImp.deleteUser("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJTYXJ1bWFuIiwiaWF0IjoxNzMyMTE0MjExLCJleHAiOjE3MzIyMDA2MTF9");
        assertThat(deletedUser).isEmpty();
    }

    @Test
    public void TestFineUserServiceDeleteMethod() {

        when(passwordEncoder.encode("cantpass")).thenReturn(new BCryptPasswordEncoder().encode("cantpass"));

        UserPass userPass = new UserPass();
        userPass.setUsername("Gandalf");
        userPass.setAdmin(false);
        userPass.setHashedPass(passwordEncoder.encode("cantpass"));

        when(userPassRepository.findByUsername(userPass.getUsername())).thenReturn(Optional.of(userPass));
        when(jwtService.extractUserPass("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJTYXJ1bWFuIiwiaWF0IjoxNzMyMTE0MjExLCJleHAiOjE3MzIyMDA2MTF9.crcagQMEN62awSn258JlKmknhFHRDOH_Jgjvqu0G7qE")).thenReturn(userPass);

        Optional<UserDTO> userDto = userServiceImp.deleteUser("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJTYXJ1bWFuIiwiaWF0IjoxNzMyMTE0MjExLCJleHAiOjE3MzIyMDA2MTF9.crcagQMEN62awSn258JlKmknhFHRDOH_Jgjvqu0G7qE");

        DeletedUser deletedUser = new DeletedUser();
        deletedUser.setUsername("Gandalf");

        assertThat(userDto).usingRecursiveComparison().isEqualTo(Optional.empty());

    }

    @Test
    public void TestFineGetAllUsers() {

        User user1 = new User();
        user1.setUsername("Gandalf");
        User user2 = new User();
        user2.setUsername("Albus");
        User user3 = new User();
        user3.setUsername("Merlin");
        User user4 = new User();
        user4.setUsername("Blaise");

        List<User> usersExpected = List.of(user1, user2, user3, user4);

        when(userRepository.findAll()).thenReturn(usersExpected);

        List<User> users = userRepository.findAll();

        assertThat(usersExpected).usingRecursiveComparison().isEqualTo(users);

    }

    @Test
    public void TestFailGetAllUsers() {


        List<User> usersExpected = Collections.emptyList();

        when(userRepository.findAll()).thenReturn(usersExpected);

        List<User> users = userRepository.findAll();

        assertThat(usersExpected).usingRecursiveComparison().isEqualTo(users);

    }

    @Test
    public void TestFailGetUserByName() {
        when(userRepository.findById("Gandalf")).thenReturn(Optional.empty());

        Optional<UserDTO> user = userServiceImp.getUserById("Gandalf");

        assertThat(user).isEmpty();
    }

    @Test
    public void TestFineGetUserByName() {

        User userExpected = new User();
        userExpected.setUsername("Gandalf");

        when(userRepository.findById("Gandalf")).thenReturn(Optional.of(userExpected));

        Optional<User> user = userRepository.findById("Gandalf");

        assertThat(Optional.of(userExpected)).usingRecursiveComparison().isEqualTo(user);
    }

}
