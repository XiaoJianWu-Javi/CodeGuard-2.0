package es.tfg.codeguard.service.imp;

import java.util.Optional;

import es.tfg.codeguard.model.repository.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import es.tfg.codeguard.service.LoginService;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;

@Service
public class LoginServiceImp implements LoginService {

    @Autowired
    private UserPassRepository userPassRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<UserPassDTO> loginUser(String userName, String userPassword) {

        if(userRepository.findById(userName).isEmpty()){
            return Optional.empty();
        }

        Optional<UserPass> userOp = userPassRepository.findByUsername(userName);

        UserPass user = userOp.get();

        if(passwordEncoder.matches(userPassword, user.getHashedPass())){
            return Optional.of(new UserPassDTO(user));
        }

        return Optional.empty();

    }
}
