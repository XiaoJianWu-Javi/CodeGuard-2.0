package es.tfg.codeguard.service.imp;

import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.model.entity.deleteduser.DeletedUser;
import es.tfg.codeguard.model.entity.user.User;
import es.tfg.codeguard.model.repository.deleteduser.DeletedUserRepository;
import es.tfg.codeguard.model.repository.user.UserRepository;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.service.JWTService;
import es.tfg.codeguard.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    //TODO: configurar las clases para cuando se tenga configurado la base de datos utilizar bcript desde config
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private UserRepository userRepository;

    private UserPassRepository userPassRepository;

    private DeletedUserRepository deletedUserRepository;

    private JWTService jwtService;

    public UserServiceImp(UserRepository userRepository, UserPassRepository userPassRepository, DeletedUserRepository deletedUserRepository, JWTService jwtService) {
        this.userRepository = userRepository;
        this.userPassRepository = userPassRepository;
        this.deletedUserRepository = deletedUserRepository;
        this.jwtService = jwtService;
    }

    public Optional<UserDTO> deleteUser(String userToken) {

        UserPassDTO userPassDTO = new UserPassDTO(jwtService.extractUserPass(userToken));

        if (userPassRepository.findById(userPassDTO.getUsername()).isEmpty()) {
            return Optional.empty();
        }

        User user = userRepository.findById(userPassDTO.getUsername()).get();

        deletedUserRepository.save(new DeletedUser(user));

        userRepository.delete(userRepository.findById(userPassDTO.getUsername()).get());
        userPassRepository.delete(userPassRepository.findById(userPassDTO.getUsername()).get());

        UserDTO userDTO = new UserDTO(user);

        return Optional.of(userDTO);
    }

    public Optional<UserDTO> getUserById(String username) {

        Optional<User> wizard = userRepository.findById(username);
        return wizard.map(UserDTO::new);
    }

    public List<UserDTO> getAllUsers() {

        return userRepository.findAll().stream().map(UserDTO::new).toList();
    }

}
