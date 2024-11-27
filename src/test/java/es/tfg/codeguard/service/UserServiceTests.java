package es.tfg.codeguard.service;


import es.tfg.codeguard.model.dto.ChangePasswordDTO;
import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.model.entity.deleteduser.DeletedUser;
import es.tfg.codeguard.model.entity.user.User;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.model.repository.deleteduser.DeletedUserRepository;
import es.tfg.codeguard.model.repository.user.UserRepository;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.service.imp.UserServiceImp;
import es.tfg.codeguard.util.PasswordNotValidException;
import es.tfg.codeguard.util.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserPassRepository userPassRepository;

    @Mock
    private DeletedUserRepository deletedUserRepository;

    @InjectMocks
    private UserServiceImp userServiceImp;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTService jwtService;

    @Test
    public void TestFailUserServiceDeleteMethod() {

        UserPass userPass = new UserPass();
        userPass.setUsername("Saruman");
        userPass.setAdmin(false);

        when(jwtService.extractUserPass("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJTYXJ1bWFuIiwiaWF0IjoxNzMyMTE0MjExLCJleHAiOjE3MzIyMDA2MTF9")).thenReturn(userPass);

        assertThrows(UserNotFoundException.class, () -> userServiceImp.deleteUser("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJTYXJ1bWFuIiwiaWF0IjoxNzMyMTE0MjExLCJleHAiOjE3MzIyMDA2MTF9"));
    }

    @Test
    public void TestFineUserServiceDeleteMethod() {

        //when(passwordEncoder.encode("cantpass")).thenReturn(new BCryptPasswordEncoder().encode("cantpass"));

        UserPass userPass = new UserPass();
        userPass.setUsername("Gandalf");

        User user = new User();
        user.setUsername("Gandalf");


        UserDTO userDTOMock = new UserDTO("Gandalf", false, false, List.of());

        when(userRepository.findById("Gandalf")).thenReturn(Optional.of(user));

        when(jwtService.extractUserPass("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJTYXJ1bWFuIiwiaWF0IjoxNzMyMTE0MjExLCJleHAiOjE3MzIyMDA2MTF9.crcagQMEN62awSn258JlKmknhFHRDOH_Jgjvqu0G7qE")).thenReturn(userPass);


        UserDTO userDto = userServiceImp.deleteUser("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJTYXJ1bWFuIiwiaWF0IjoxNzMyMTE0MjExLCJleHAiOjE3MzIyMDA2MTF9.crcagQMEN62awSn258JlKmknhFHRDOH_Jgjvqu0G7qE");

        DeletedUser deletedUser = new DeletedUser();
        deletedUser.setUsername("Gandalf");

        assertThat(userDto).usingRecursiveComparison().isEqualTo(new UserDTO(deletedUser));

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
        when(userRepository.findById("d2hoqdhqiuhduwd")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userServiceImp.getUserById("d2hoqdhqiuhduwd"));
    }

    @Test
    public void TestFineGetUserByName() {

        User userExpected = new User();
        userExpected.setUsername("Gandalf");

        when(userRepository.findById("Gandalf")).thenReturn(Optional.of(userExpected));

        Optional<User> user = userRepository.findById("Gandalf");

        assertThat(Optional.of(userExpected)).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void ChangeUserPasswordServiceTest(){

        String houdiniToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJIb3VkaW5pIiwiaWF0IjoxNzMyNzE0NTU4LCJleHAiOjE3MzI4MDA5NTh9.qBlNfje8pDRtoKzZWmfPsNQf6qKOpGOqsqvzNxlq9Gw";

        String rachelToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJSYWNoZWwiLCJpYXQiOjE3MzI3MTQ2MzAsImV4cCI6MTczMjgwMTAzMH0.G3dIq1wo-zJCvHqAocDWTXEfaqT1wsyf1ddqXk1B9bM";

        String drStrangeToken= "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJEclN0cmFuZ2UiLCJpYXQiOjE3MzI3MTQ3NTYsImV4cCI6MTczMjgwMTE1Nn0.615S7M2Bi9CK4q7NksMyeHs4YnGyCVouKmRQdXxvvFM";

        String dinamoToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJEeW5hbW8iLCJpYXQiOjE3MzI3MTUwNTIsImV4cCI6MTczMjgwMTQ1Mn0.mFa0MeoxrjZ0TUtFftHp2SbEYNioNazfWaLhTEbEetI";

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        UserDTO expectedUser = new UserDTO("Houdini",false,false,List.of());

        UserPass userPass = new UserPass("Houdini", passwordEncoder.encode("1234"), false);

        when(userPassRepository.findById(jwtService.extractUserPass(houdiniToken).getUsername())).thenReturn(Optional.of(userPass));

        UserDTO resultUser = userServiceImp.changePassword(houdiniToken, new ChangePasswordDTO("1234", "newSecurePassword1234"));

        assertThat(expectedUser).usingRecursiveComparison().isEqualTo(resultUser);



        expectedUser = new UserDTO("Rachel",false,false,List.of());

        userPass = new UserPass("Rachel", passwordEncoder.encode("9876"), false);

        when(userPassRepository.findById(jwtService.extractUserPass(rachelToken).getUsername())).thenReturn(Optional.of(userPass));

        resultUser = userServiceImp.changePassword(rachelToken, new ChangePasswordDTO("9876", "newSecurePassword9876"));

        assertThat(expectedUser).usingRecursiveComparison().isEqualTo(resultUser);



        expectedUser = new UserDTO("DrStrange",false,false,List.of());

        userPass = new UserPass("DrStrange", passwordEncoder.encode("0000"), false);

        when(userPassRepository.findById(jwtService.extractUserPass(drStrangeToken).getUsername())).thenReturn(Optional.of(userPass));

        resultUser = userServiceImp.changePassword(drStrangeToken, new ChangePasswordDTO("0000", "DrStrangeHelpPeople67"));

        assertThat(expectedUser).usingRecursiveComparison().isEqualTo(resultUser);



        expectedUser = new UserDTO("Dinamo",false,false,List.of());

        userPass = new UserPass("Dinamo", passwordEncoder.encode("a"), false);

        when(userPassRepository.findById(jwtService.extractUserPass(dinamoToken).getUsername())).thenReturn(Optional.of(userPass));

        resultUser = userServiceImp.changePassword(dinamoToken, new ChangePasswordDTO("a", "DinamoTheBestWizardOfTheWordl69"));

        assertThat(expectedUser).usingRecursiveComparison().isEqualTo(resultUser);

    }

    @ParameterizedTest
    @ValueSource(strings = {"D3xter", "R4ch3l", "4r3121marck", "b0b"})
    void FailChangePasswordUserNotFoundTest(String username){

        when(userRepository.findById(username)).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> userServiceImp.changePassword(jwtService.createJwt(new UserPassDTO(username,false)), new ChangePasswordDTO("1234", "SecurePassword1234+")));

    }

    @ParameterizedTest
    @ValueSource(strings = {"D3xter", "R4ch3l", "4r3121marck", "b0b"})
    void FailChangePasswordNotValidTest(String username){

        when(userRepository.findById(username)).thenThrow(PasswordNotValidException.class);

        assertThrows(PasswordNotValidException.class, () -> userServiceImp.changePassword(jwtService.createJwt(new UserPassDTO(username,false)), new ChangePasswordDTO("1234", "")));

    }



}
