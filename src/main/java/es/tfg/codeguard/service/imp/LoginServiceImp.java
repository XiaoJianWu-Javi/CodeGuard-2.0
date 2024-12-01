package es.tfg.codeguard.service.imp;

import es.tfg.codeguard.model.dto.AuthDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.model.repository.user.UserRepository;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.service.LoginService;
import es.tfg.codeguard.util.IncorrectPasswordException;
import es.tfg.codeguard.util.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImp implements LoginService {

    @Autowired
    private UserPassRepository userPassRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserPassDTO loginUser(AuthDTO authDTO) {

        if (userRepository.findById(authDTO.username()).isEmpty()) {
            throw new UserNotFoundException("User not found [" + authDTO.username() + "]");
        }

        Optional<UserPass> userOp = userPassRepository.findByUsername(authDTO.username());

        UserPass user = userOp.get();

        if (!passwordEncoder.matches(authDTO.password(), user.getHashedPass())) {
            throw new IncorrectPasswordException("Incorrect password" + "for user [" + authDTO.username() + "]");
        }

        return new UserPassDTO(user);

    }
}
