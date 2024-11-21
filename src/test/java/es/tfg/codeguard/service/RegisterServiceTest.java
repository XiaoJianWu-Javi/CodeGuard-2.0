package es.tfg.codeguard.service;

import es.tfg.codeguard.model.dto.AuthDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.model.repository.deleteduser.DeletedUserRepository;
import es.tfg.codeguard.model.repository.user.UserRepository;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.service.imp.RegisterServiceImp;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class RegisterServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserPassRepository userPassRepository;

    @Mock
    private DeletedUserRepository deletedUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegisterServiceImp registerServiceImp;

    private AuthDTO jsonParserDTO;

    @BeforeEach
    void setup() {
        jsonParserDTO = new AuthDTO("Gandalf", "cantpass");
    }

    @Test
    public void TestFailUserServiceRegisterMethod() {

        UserPass userPass = new UserPass();
        userPass.setUsername("Gandalf");

        when(userPassRepository.findById("Gandalf")).thenReturn(Optional.of(userPass));

        Optional<UserPassDTO> userOpt = registerServiceImp.registerUser(jsonParserDTO);

        assertThat(userOpt).isEmpty();
    }

    @Test
    public void TestFineUserServiceRegisterMethod() {

        when(passwordEncoder.encode("cantpass")).thenReturn(new BCryptPasswordEncoder().encode("cantpass"));
        when(userPassRepository.findById("Gandalf")).thenReturn(Optional.empty());

        Optional<UserPassDTO> user = registerServiceImp.registerUser(jsonParserDTO);

        UserPassDTO userPassDTO = new UserPassDTO("Gandalf", false);
        Optional<UserPassDTO> userExpected = Optional.of(userPassDTO);
        assertThat(userExpected).usingRecursiveComparison().isEqualTo(user);
    }

}
