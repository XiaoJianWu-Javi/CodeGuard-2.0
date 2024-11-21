package es.tfg.codeguard.service.imp;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.entity.deleteduser.DeletedUser;
import es.tfg.codeguard.model.entity.user.User;
import es.tfg.codeguard.model.repository.deleteduser.DeletedUserRepository;
import es.tfg.codeguard.model.repository.user.UserRepository;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.service.UserService;

@Service
public class UserServiceImp implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserPassRepository userPassRepository;
    private final DeletedUserRepository deletedUserRepository;

    public UserServiceImp(PasswordEncoder passwordEncoder, UserRepository userRepository,
            UserPassRepository userPassRepository, DeletedUserRepository deletedUserRepository) {
                
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userPassRepository = userPassRepository;
        this.deletedUserRepository = deletedUserRepository;
    }

    @Override
    //TODO: Este metodo con spring session tiene que detectar la sesion y si estas conectado te cierra la sesion y te elimina. (No recibe parametros)
    public Optional<UserDTO> deleteUser(String username) {

        if(userPassRepository.findById(username).isEmpty()){
            return Optional.empty();
        }

        User user = userRepository.findById(username).get();

        deletedUserRepository.save(new DeletedUser(user));

        userRepository.delete(userRepository.findById(username).get());
        userPassRepository.delete(userPassRepository.findById(username).get());

        return Optional.of(new UserDTO(user));
    }

    @Override
    public Optional<UserDTO> getUserById(String username){

        Optional<User> userOptional = userRepository.findById(username);
        return userOptional.map(UserDTO::new);
    }

    @Override
    public List<UserDTO> getAllUsers() {

        return userRepository.findAll().stream().map(UserDTO::new).toList();
    }

}
