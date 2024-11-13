package es.tfg.codeguard.service.imp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.model.repository.user.UserRepository;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.service.LoginService;

@Service
public class LoginServiceImp implements LoginService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPassRepository userPassRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserPass> user = userPassRepository.findById(username);

        if (user.isPresent()) {
            UserPass userObj = user.get();

            return User.builder()
                .username(userObj.getUsername())
                .password(userObj.getHashedPass())
                .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}
