package es.tfg.codeguard.service.imp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.service.CodeGuardUserDetailsService;

@Service
public class CodeGuardUserDetailsServiceImp implements CodeGuardUserDetailsService {

    @Autowired
    private UserPassRepository userPassRepository;

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
        if (user.isAdmin()) return new String[] {"ADMIN", "USER"};
        else return new String[] {"USER"};
    }

}
