package es.tfg.codeguard.service.imp;

import es.tfg.codeguard.model.dto.AuthDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.model.entity.user.User;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.model.repository.user.UserRepository;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.service.RegisterService;
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
    public Optional<UserPassDTO> registerUser(AuthDTO authDTO) throws IllegalArgumentException {

        if (userPassRepository.findById(authDTO.username()).isPresent()) {
            return Optional.empty();
        }

        UserPass userPassEncript = new UserPass();

        try {
            userPassEncript.setUsername(authDTO.username());
            userPassEncript.setHashedPass(passwordEncoder.encode(authDTO.password()));
        } catch (IllegalArgumentException e) {
            throw new UsernameNotValidException("NOMBRE O CONTRASEÑA INCORRECTO");
        }

        userPassEncript.setAdmin(false);
        userPassRepository.save(userPassEncript);

        userRepository.save(new User(authDTO.username()));

        return Optional.of(new UserPassDTO(userPassEncript));

    }

}
