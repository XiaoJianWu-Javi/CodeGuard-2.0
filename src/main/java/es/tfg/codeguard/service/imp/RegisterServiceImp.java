package es.tfg.codeguard.service.imp;

import es.tfg.codeguard.model.dto.AuthDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.model.entity.user.User;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.model.repository.user.UserRepository;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.service.RegisterService;
import es.tfg.codeguard.util.PasswordNotValidException;
import es.tfg.codeguard.util.UsernameInUseException;
import es.tfg.codeguard.util.UsernameNotValidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisterServiceImp implements RegisterService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserPassRepository userPassRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserPassDTO registerUser(AuthDTO authDTO) {

        if (userPassRepository.findById(authDTO.username()).isPresent()) {
            throw new UsernameInUseException("Username is already in use [" +authDTO.username() +"]");
        }

        UserPass userPass = new UserPass();
        userPass.setAdmin(false);

        try {
            userPass.setUsername(authDTO.username());
            userPass.setHashedPass(passwordEncoder.encode(authDTO.password()));
        } catch (UsernameNotValidException e) {
            throw new UsernameNotValidException("Username not valid [" +authDTO.username() +"]");
        } catch (PasswordNotValidException i){
            throw new PasswordNotValidException("Password not valid [" +authDTO.password() +"]");
        }

        userPassRepository.save(userPass);

        userRepository.save(new User(authDTO.username()));

        return new UserPassDTO(userPass);

    }

}
