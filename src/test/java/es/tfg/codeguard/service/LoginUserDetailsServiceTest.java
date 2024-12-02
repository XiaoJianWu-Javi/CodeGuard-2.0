package es.tfg.codeguard.service;

import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.service.imp.LoginUserDetailsServiceImp;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class LoginUserDetailsServiceTest {

    @Mock
    private UserPassRepository userPassRepository;

    @InjectMocks
    private LoginUserDetailsServiceImp loginUserDetailsServiceImp;

    @ParameterizedTest
    @ValueSource(strings = {"Rachel", "Damian", "Adan", "helpme991", "Dext3r"})
    void loadUserDetailsTest(String username) {

        String hashedPass = new BCryptPasswordEncoder().encode("1234");

        when(userPassRepository.findByUsername(username)).thenReturn(Optional.of(new UserPass(username, hashedPass, false)));

        List<GrantedAuthority> expectedAuthorityList = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        UserDetails expectedUserDeatils = new User(username, hashedPass, expectedAuthorityList);

        UserDetails resultUserDetails = loginUserDetailsServiceImp.loadUserByUsername(username);

        assertThat(expectedUserDeatils).usingRecursiveComparison().isEqualTo(resultUserDetails);

    }

    @ParameterizedTest
    @ValueSource(strings = {"Rachel", "Damian", "Adan", "helpme991", "Dext3r"})
    void loadAdminUserDetailsTest(String username) {

        String hashedPass = new BCryptPasswordEncoder().encode("1234");

        when(userPassRepository.findByUsername(username)).thenReturn(Optional.of(new UserPass(username, hashedPass, true)));

        List<GrantedAuthority> expectedAuthorityList = List.of(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN"));

        UserDetails expectedUserDeatils = new User(username, hashedPass, expectedAuthorityList);

        UserDetails resultUserDetails = loginUserDetailsServiceImp.loadUserByUsername(username);

        assertThat(expectedUserDeatils).usingRecursiveComparison().isEqualTo(resultUserDetails);

    }

}
