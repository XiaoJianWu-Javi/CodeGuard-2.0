package es.tfg.codeguard.components;

import es.tfg.codeguard.model.entity.user.User;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.model.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataIntializer {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPassRepository userPassRepository;

    @Bean
    public void firstAdmin(){
        User firstAdmin = new User("Saruman");
        firstAdmin.setTester(true);
        firstAdmin.setCreator(true);

        UserPass firstAdminPass= new UserPass();
        firstAdminPass.setUsername("Saruman");
        firstAdminPass.setAdmin(true);
        firstAdminPass.setHashedPass(passwordEncoder.encode("s4rum4n"));

        userRepository.save(firstAdmin);
        userPassRepository.save(firstAdminPass);
    }

}
