package es.tfg.codeguard.service.imp;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.tfg.codeguard.model.dto.AuthDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.model.entity.user.User;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.model.repository.deleteduser.DeletedUserRepository;
import es.tfg.codeguard.model.repository.user.UserRepository;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.service.RegisterService;
import es.tfg.codeguard.util.UsernameNotValid;

@Service
public class RegisterServiceImp implements RegisterService {

    private final PasswordEncoder passwordEncoder;
    private final UserPassRepository userPassRepository;
    private final UserRepository userRepository;
    private final DeletedUserRepository deletedUserRepository;

    // TODO: TERMINAR SERVICIO

    public RegisterServiceImp(PasswordEncoder passwordEncoder, UserPassRepository userPassRepository,
            UserRepository userRepository, DeletedUserRepository deletedUserRepository) {
                
        this.passwordEncoder = passwordEncoder;
        this.userPassRepository = userPassRepository;
        this.userRepository = userRepository;
        this.deletedUserRepository = deletedUserRepository;
    }

    @Override
    public Optional<UserPassDTO> registerUser(AuthDTO authDTO) throws IllegalArgumentException{

        if(userPassRepository.findById(authDTO.username()).isPresent()){
            return Optional.empty();
        }

        UserPass userPassEncript = new UserPass();

        try{
            userPassEncript.setUsername(authDTO.username());
            userPassEncript.setHashedPass(passwordEncoder.encode(authDTO.password()));
        }catch (IllegalArgumentException e){
            throw new UsernameNotValid("NOMBRE O CONTRASEÑA INCORRECTO");
        }

        userPassEncript.setAdmin(false);
        userPassRepository.save(userPassEncript);


        userRepository.save(new User(authDTO.username()));


        return Optional.of(new UserPassDTO(userPassEncript));

    }

}
