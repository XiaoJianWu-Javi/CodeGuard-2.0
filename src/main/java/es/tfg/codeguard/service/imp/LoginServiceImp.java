package es.tfg.codeguard.service.imp;


import es.tfg.codeguard.configuration.SecurityConfig;
import es.tfg.codeguard.controller.UserController;
import es.tfg.codeguard.controller.imp.UserControllerImp;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImp implements LoginService {

    @Autowired
    UserPassRepository userPassRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

//    @Autowired
//    SecurityConfig securityConfig;
//
//    @Autowired
//    PasswordEncoder passwordEncoder;

    @Override
    public Optional<UserPassDTO> loginUser(String userName, String userPassword) {

        Optional<UserPass> userOp = userPassRepository.findByUsername(userName);

        if(userOp.isEmpty()){
            return Optional.empty();
        }

        UserPass user = userOp.get();

        if(passwordEncoder.matches(userPassword, user.getHashedPass())){
            return Optional.of(new UserPassDTO(user));
        }

        return Optional.empty();

    }
}
