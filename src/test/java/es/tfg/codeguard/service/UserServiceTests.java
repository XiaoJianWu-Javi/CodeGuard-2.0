package es.tfg.codeguard.service;


import es.tfg.codeguard.model.dto.ChangePasswordDTO;
import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserRestoreDTO;
import es.tfg.codeguard.model.entity.deleteduser.DeletedUser;
import es.tfg.codeguard.model.entity.user.User;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.model.repository.deleteduser.DeletedUserRepository;
import es.tfg.codeguard.model.repository.user.UserRepository;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.service.imp.UserServiceImp;
import es.tfg.codeguard.util.IncorrectPasswordException;
import es.tfg.codeguard.util.PasswordNotValidException;
import es.tfg.codeguard.util.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

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
    public void deleteUserServiceTestUserNotFound() {

        UserPass userPass = new UserPass();
        userPass.setUsername("Saruman");
        userPass.setAdmin(false);

        when(jwtService.extractUserPass("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJTYXJ1bWFuIiwiaWF0IjoxNzMyMTE0MjExLCJleHAiOjE3MzIyMDA2MTF9")).thenReturn(userPass);

        assertThrows(UserNotFoundException.class, () -> userServiceImp.deleteUser("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJTYXJ1bWFuIiwiaWF0IjoxNzMyMTE0MjExLCJleHAiOjE3MzIyMDA2MTF9"));
    }

    @Test
    public void deleteUserServiceTest() {

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
    public void restoreUserServiceTestNotFound() {
    	UserRestoreDTO userRestoreDTO=new UserRestoreDTO("Javi", "password");
    	when(deletedUserRepository.findByUsername("Javi")).thenReturn(Optional.empty());
    	
    	assertThrows(UserNotFoundException.class, ()-> userServiceImp.restoreUser(userRestoreDTO.userName(), userRestoreDTO.password()));
    }
    
    @Test
    public void restoreUserServiceTestInvalidPass() {
    	String passwordHash="lklkhdskfkdfd298392";
    	
    	UserRestoreDTO userRestoreDTO=new UserRestoreDTO("Javi", "password");

    	DeletedUser user=new DeletedUser();
    	user.setUsername("Javi");
    	
    	UserPass pass=new UserPass();
    	pass.setUsername("Javi");
    	pass.setHashedPass(passwordHash);
    	
    	when(deletedUserRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
    	
    	when(userPassRepository.findById("Javi")).thenReturn(Optional.of(pass));
    	
    	when(passwordEncoder.matches("password", pass.getHashedPass())).thenReturn(false);
    	
    	assertThrows(IncorrectPasswordException.class, ()-> userServiceImp.restoreUser(userRestoreDTO.userName(), userRestoreDTO.password()));
    }
    
    @Test
    public void restoreUserServiceTest() {
    	String passwordHash="lklkhdskfkdfd298392";
    	
    	UserRestoreDTO userRestoreDTO=new UserRestoreDTO("Javi", "password");

    	DeletedUser user=new DeletedUser();
    	user.setUsername("Javi");
    	
    	UserPass pass=new UserPass();
    	pass.setUsername("Javi");
    	pass.setHashedPass(passwordHash);
    	
    	when(deletedUserRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
    	
    	when(userPassRepository.findById("Javi")).thenReturn(Optional.of(pass));
    	
    	when(passwordEncoder.matches("password", pass.getHashedPass())).thenReturn(true);
    	
    	assertEquals(new UserDTO(user), userServiceImp.restoreUser(userRestoreDTO.userName(), userRestoreDTO.password()));
    }
    
    @Test
    public void getAllUsersServiceTest() {

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
    public void getAllUsersServiceTestUsersEmpty() {

        List<User> usersExpected = Collections.emptyList();

        when(userRepository.findAll()).thenReturn(usersExpected);

        List<User> users = userRepository.findAll();

        assertThat(usersExpected).usingRecursiveComparison().isEqualTo(users);

    }

    @Test
    public void getUserByNameServiceTestUserNotFound() {
        when(userRepository.findById("d2hoqdhqiuhduwd")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userServiceImp.getUserById("d2hoqdhqiuhduwd"));
    }

    @Test
    public void getUserByNameServiceTest() {

        User userExpected = new User();
        userExpected.setUsername("Gandalf");

        when(userRepository.findById("Gandalf")).thenReturn(Optional.of(userExpected));

        Optional<User> user = userRepository.findById("Gandalf");

        assertThat(Optional.of(userExpected)).usingRecursiveComparison().isEqualTo(user);
    }

    @ParameterizedTest
    @CsvSource({
            "Dext3r,\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJEZXh0M3IiLCJpYXQiOjE3MzI3NDc4NjMsImV4cCI6MTczMjgzNDI2M30.xzGABrgSmqQ3LnqdUb8QeRuX24bFYLQHtvwzA1V77hU\",1234",
            "Rach3l,\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJSYWNoM2wiLCJpYXQiOjE3MzI3NDc5MDMsImV4cCI6MTczMjgzNDMwM30.dmCsleYmCWJIbi_dvB7CN4IplZds_NajiAo5OWlgFlE\",9876",
            "marck43rc2,\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJtYXJjazQzcmMyIiwiaWF0IjoxNzMyNzQ3OTMxLCJleHAiOjE3MzI4MzQzMzF9.b3hkIexqnK67XcMNWeSK_0gaITgwsgiEX2b9xaMOlLk\",hello",
            "bob0b0b,\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJib2IwYjBiIiwiaWF0IjoxNzMyNzQ3OTU0LCJleHAiOjE3MzI4MzQzNTR9.M-hAOdsXunlo0PDDyziTiha_k2vCqrkTSa6LXOKx8W4\",notsecure"
    })
    void changeUserPasswordServiceTestUserNotFoundTest(String username, String token, String currentPassword) {

        when(jwtService.extractUserPass(token)).thenReturn(new UserPass(username, new BCryptPasswordEncoder().encode(currentPassword), false));

        when(userRepository.findById(username)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userServiceImp.changePassword(token, new ChangePasswordDTO("1234", "SecurePassword1234+")));

    }

    @ParameterizedTest
    @CsvSource({
            "Dext3r,\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJEZXh0M3IiLCJpYXQiOjE3MzI3NDc4NjMsImV4cCI6MTczMjgzNDI2M30.xzGABrgSmqQ3LnqdUb8QeRuX24bFYLQHtvwzA1V77hU\",1234,",
            "Rach3l,\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJSYWNoM2wiLCJpYXQiOjE3MzI3NDc5MDMsImV4cCI6MTczMjgzNDMwM30.dmCsleYmCWJIbi_dvB7CN4IplZds_NajiAo5OWlgFlE\",9876,  ",
            "marck43rc2,\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJtYXJjazQzcmMyIiwiaWF0IjoxNzMyNzQ3OTMxLCJleHAiOjE3MzI4MzQzMzF9.b3hkIexqnK67XcMNWeSK_0gaITgwsgiEX2b9xaMOlLk\",hello,                                           ",
            "bob0b0b,\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJib2IwYjBiIiwiaWF0IjoxNzMyNzQ3OTU0LCJleHAiOjE3MzI4MzQzNTR9.M-hAOdsXunlo0PDDyziTiha_k2vCqrkTSa6LXOKx8W4\",notsecure,"
    })
    void changeUserPasswordServiceTestUserNotFoundTest(String username, String userToken, String oldPassword, String newPassword) {

        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO(oldPassword, newPassword);

        assertThrows(PasswordNotValidException.class, () -> userServiceImp.changePassword(userToken, changePasswordDTO));

    }

    @ParameterizedTest
    @CsvSource({
            "Dext3r,\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJEZXh0M3IiLCJpYXQiOjE3MzI3NDc4NjMsImV4cCI6MTczMjgzNDI2M30.xzGABrgSmqQ3LnqdUb8QeRuX24bFYLQHtvwzA1V77hU\",thisIsNotAPassword,newSecurePassword+",
            "Rach3l,\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJSYWNoM2wiLCJpYXQiOjE3MzI3NDc5MDMsImV4cCI6MTczMjgzNDMwM30.dmCsleYmCWJIbi_dvB7CN4IplZds_NajiAo5OWlgFlE\",9876,1234567",
            "marck43rc2,\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJtYXJjazQzcmMyIiwiaWF0IjoxNzMyNzQ3OTMxLCJleHAiOjE3MzI4MzQzMzF9.b3hkIexqnK67XcMNWeSK_0gaITgwsgiEX2b9xaMOlLk\",hello,goodbye",
            "bob0b0b,\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJib2IwYjBiIiwiaWF0IjoxNzMyNzQ3OTU0LCJleHAiOjE3MzI4MzQzNTR9.M-hAOdsXunlo0PDDyziTiha_k2vCqrkTSa6LXOKx8W4\",notsecure,moresecure"
    })
    void changeUserPasswordServiceTestIncorrectPassword(String username, String userToken, String oldPassword, String newPassword) {

        when(userRepository.findById(username)).thenReturn(Optional.of(new User("Dext3r")));

        when(jwtService.extractUserPass(userToken)).thenReturn(new UserPass(username, "1234", false));

        when(userPassRepository.findById(username)).thenReturn(Optional.of(new UserPass(username, new BCryptPasswordEncoder().encode("1234"), false)));

        assertThrows(IncorrectPasswordException.class, () -> userServiceImp.changePassword(userToken, new ChangePasswordDTO(oldPassword, newPassword)));

    }

}