package es.tfg.codeguard.service.imp;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.service.LoginUserDetailsService;

@Service
public class LoginUserDetailsServiceImp implements LoginUserDetailsService {

    private final UserPassRepository userPassRepository;

    public LoginUserDetailsServiceImp(UserPassRepository userPassRepository) {
        this.userPassRepository = userPassRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserPass> user = userPassRepository.findByUsername(username);
        if (user.isPresent()) {
            UserPass userObject = user.get();

            return User.builder()
                    .username(userObject.getUsername())
                    .password(userObject.getHashedPass())
                    .roles(getRol(userObject))
                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    private String[] getRol(UserPass user) {
        if (Boolean.TRUE.equals(user.isAdmin())) return new String[] {"ADMIN", "USER"};
        else return new String[] {"USER"};
    }

}
