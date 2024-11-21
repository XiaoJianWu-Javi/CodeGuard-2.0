package es.tfg.codeguard.service.imp;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.model.entity.deleteduser.DeletedUser;
import es.tfg.codeguard.model.entity.user.User;
import es.tfg.codeguard.model.repository.deleteduser.DeletedUserRepository;
import es.tfg.codeguard.model.repository.user.UserRepository;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.service.UserService;
import es.tfg.codeguard.service.JWTService;

@Service
public class UserServiceImp implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserPassRepository userPassRepository;
    private final DeletedUserRepository deletedUserRepository;
    private final JWTService jwtService;

    public UserServiceImp(PasswordEncoder passwordEncoder, UserRepository userRepository,
            UserPassRepository userPassRepository, DeletedUserRepository deletedUserRepository, JWTService jwtService) {

        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userPassRepository = userPassRepository;
        this.deletedUserRepository = deletedUserRepository;
        this.jwtService = jwtService;
    }

    @Override
    public Optional<UserDTO> deleteUser(String userToken) {

        UserPassDTO userPassDTO = new UserPassDTO(jwtService.extractUserPass(userToken));

        if (userPassRepository.findById(userPassDTO.username()).isEmpty()) {
            return Optional.empty();
        }

        User user = userRepository.findById(userPassDTO.username()).get();

        deletedUserRepository.save(new DeletedUser(user));

        userRepository.delete(userRepository.findById(userPassDTO.username()).get());
        userPassRepository.delete(userPassRepository.findById(userPassDTO.username()).get());

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
