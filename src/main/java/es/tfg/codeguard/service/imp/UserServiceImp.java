package es.tfg.codeguard.service.imp;

import es.tfg.codeguard.model.dto.ChangePasswordDTO;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.util.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserPassRepository userPassRepository;
    @Autowired
    private DeletedUserRepository deletedUserRepository;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO deleteUser(String userToken) {

        UserPassDTO userPassDTO = new UserPassDTO(jwtService.extractUserPass(userToken));

        Optional<User> userOptional = userRepository.findById(userPassDTO.username());

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found [" + userPassDTO.username() + "]");
        }

        User user = userOptional.get();

        deletedUserRepository.save(new DeletedUser(user));

        userRepository.delete(user);

        return new UserDTO(user);
    }

    @Override
    public UserDTO getUserById(String username) {

        Optional<User> userOptional = userRepository.findById(username);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found [" + username + "]");
        }

        return new UserDTO(userOptional.get());
    }

    @Override
    public List<UserDTO> getAllUsers() {

        return userRepository.findAll().stream().map(UserDTO::new).toList();
    }

    @Override
    public UserDTO changePassword(String userToken, ChangePasswordDTO changePasswordDTO) {

        //POSIBLE IF COMPROBANDO SI LA CONTRASEÑA ES VÁLIDA

        String username = jwtService.extractUserPass(userToken).getUsername();

        Optional<User> userOptional = userRepository.findById(username);

        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User not found [ " +username +" ]");
        }

        UserPass userPass = userPassRepository.findById(username).get();

        if(passwordEncoder.matches(changePasswordDTO.oldPasword(), userPass.getHashedPass())){

        }

        return null;

    }

}
