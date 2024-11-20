package es.tfg.codeguard.service.imp;

import es.tfg.codeguard.model.dto.JsonParserUserPassDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.model.entity.user.User;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.model.repository.deleteduser.DeletedUserRepository;
import es.tfg.codeguard.model.repository.user.UserRepository;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.service.RegisterService;
import es.tfg.codeguard.util.UsernameNotValidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisterServiceImp implements RegisterService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserPassRepository userPassRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeletedUserRepository deletedUserRepository;

    public Optional<UserPassDTO> registerUser(JsonParserUserPassDTO jsonParserUserPassDTO) throws IllegalArgumentException {

        if (userPassRepository.findById(jsonParserUserPassDTO.getUsername()).isPresent()) {
            return Optional.empty();
        }

        UserPass userPassEncript = new UserPass();

        try {
            userPassEncript.setUsername(jsonParserUserPassDTO.getUsername());
            userPassEncript.setHashedPass(passwordEncoder.encode(jsonParserUserPassDTO.getPassword()));
        } catch (IllegalArgumentException e) {
            throw new UsernameNotValidException("NOMBRE O CONTRASEÑA INCORRECTO");
        }

        userPassEncript.setAdmin(false);
        userPassRepository.save(userPassEncript);

        userRepository.save(new User(jsonParserUserPassDTO.getUsername()));


        return Optional.of(new UserPassDTO(userPassEncript));

    }

}
