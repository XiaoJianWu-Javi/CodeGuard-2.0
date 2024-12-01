package es.tfg.codeguard.service;

import es.tfg.codeguard.model.dto.AuthDTO;
import es.tfg.codeguard.model.entity.user.User;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.model.repository.user.UserRepository;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.service.imp.LoginServiceImp;
import es.tfg.codeguard.util.IncorrectPasswordException;
import es.tfg.codeguard.util.UserNotFoundException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserPassRepository userPassRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private LoginServiceImp loginServiceImp;

    @ParameterizedTest
    @ValueSource(strings = {"D3xter", "4D4n", "991helpme", "hhewe¡¡?¿¿?"})
    void loginUserTestUserNotFound(String username) {

        AuthDTO authDTO = new AuthDTO(username, "9876PasswordDuui");

        when(userRepository.findById(username)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> loginServiceImp.loginUser(authDTO));

    }

    @ParameterizedTest
    @ValueSource(strings = {"Dext3r", "Adan", "helpme991", "hhewehew"})
    void loginUserTestIncorrectPassword(String username) {

        String hashedPass = new BCryptPasswordEncoder().encode("1234");

        AuthDTO authDTO = new AuthDTO(username, "9876Password");

        when(userRepository.findById(username)).thenReturn(Optional.of(new User(username, false, false)));

        when(userPassRepository.findByUsername(username)).thenReturn(Optional.of(new UserPass(username, hashedPass, false)));

        assertThrows(IncorrectPasswordException.class, () -> loginServiceImp.loginUser(authDTO));

    }

}