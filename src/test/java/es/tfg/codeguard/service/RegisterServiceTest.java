package es.tfg.codeguard.service;

import es.tfg.codeguard.model.dto.JsonParserUserPassDTO;
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

    @InjectMocks
    private RegisterServiceImp registerServiceImp;

    private JsonParserUserPassDTO jsonParserDTO;

    @BeforeEach
    void setup(){

        jsonParserDTO = new JsonParserUserPassDTO();

    }

    @Test
    public void TestFailUserServiceRegisterMethod() {

        jsonParserDTO.setUsername("Gandalf");
        jsonParserDTO.setPassword("cantpass");

        UserPass userPass = new UserPass();
        userPass.setUsername("Gandalf");

        when(userPassRepository.findById("Gandalf")).thenReturn(Optional.of(userPass));

        Optional<UserPassDTO> userOpt = registerServiceImp.registerUser(jsonParserDTO);

        assertThat(userOpt).isEmpty();
    }

    @Test
    public void TestFineUserServiceRegisterMethod() {

        jsonParserDTO.setUsername("Gandalf");
        jsonParserDTO.setPassword("cantpass");

        when(userPassRepository.findById("Gandalf")).thenReturn(Optional.empty());

        Optional<UserPassDTO> user = registerServiceImp.registerUser(jsonParserDTO);

        UserPassDTO userPassDTO = new UserPassDTO();
        userPassDTO.setUsername("Gandalf");
        userPassDTO.setAdmin(false);
        Optional<UserPassDTO> userExpected = Optional.of(userPassDTO);
        assertThat(userExpected).usingRecursiveComparison().isEqualTo(user);
    }

}
