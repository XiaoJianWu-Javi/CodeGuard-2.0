package es.tfg.codeguard.service;

import es.tfg.codeguard.model.dto.CompilerRequestDTO;
import es.tfg.codeguard.model.dto.CompilerResponseDTO;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.util.CompilationErrorException;
import es.tfg.codeguard.util.TestCasesNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CompilerServiceTests {

    @Mock
    private UserPassRepository userPassRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JWTService jwtService;
    @Autowired
    CompilerService compilerService;

    @Test
    void testingValidSolution(){

        //Los token tienen caducidad asique este test mañana cuando se caduque el token va a fallar
        String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJTYXJ1bWFuIiwiaWF0IjoxNzMyMTE0MjExLCJleHAiOjE3MzIyMDA2MTF9.crcagQMEN62awSn258JlKmknhFHRDOH_Jgjvqu0G7qE";

        when(passwordEncoder.encode("cantpass")).thenReturn(new BCryptPasswordEncoder().encode("cantpass"));

        UserPass userPass = new UserPass();
        userPass.setUsername("Gandalf");
        userPass.setAdmin(false);
        userPass.setHashedPass(passwordEncoder.encode("cantpass"));

        when(userPassRepository.findByUsername(userPass.getUsername())).thenReturn(Optional.of(userPass));
        when(jwtService.extractUserPass(jwtToken)).thenReturn(userPass);

        CompilerRequestDTO requestDTO = new CompilerRequestDTO(
            "plural","public class Plural{ public static boolean isPlural(float f){ return f !=1;}}"
        );

        try {
            Optional<CompilerResponseDTO> responseDTO = compilerService.compile(jwtToken, requestDTO);
            assertThat(responseDTO.get().executionCode()).isEqualTo(0);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CompilationErrorException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (TestCasesNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}
