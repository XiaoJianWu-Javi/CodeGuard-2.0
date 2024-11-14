package es.tfg.codeguard.service.imp;

import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.model.entity.deleteduser.DeletedUser;
import es.tfg.codeguard.model.entity.user.User;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.model.repository.deleteduser.DeletedUserRepository;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.model.repository.user.UserRepository;
import es.tfg.codeguard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    //TODO: configurar las clases para cuando se tenga configurado la base de datos utilizar bcript desde config
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserPassRepository userPassRepository;
    @Autowired
    private DeletedUserRepository deletedUserRepository;

//    @Override
//    public Optional<UserPassDTO> registerUser(String userName, String userPass) {
//
//        if (userPassRepository.findById(userName).isPresent()) {
//            return Optional.empty();
//        }
//
//        User user = new User(userName);
//        userRepository.save(user);
//
//        UserPass userPassEncript = new UserPass();
//        userPassEncript.setUsername(userName);
//        userPassEncript.setAdmin(false);
//        userPassEncript.setHashedPass(passwordEncoder.encode(userPass));
//        userPassRepository.save(userPassEncript);
//
//        UserPassDTO userPassDTO = new UserPassDTO(userPassEncript);
//
//        return Optional.of(userPassDTO);
//    }

    //TODO: Este metodo con spring session tiene que detectar la sesion y si estas conectado te cierra la sesion y te elimina. (No recibe parametros)
    public Optional<UserDTO> deleteUser(String userName) {

        if(userPassRepository.findById(userName).isEmpty()){
            return Optional.empty();
        }

        User user = userRepository.findById(userName).get();

        deletedUserRepository.save(new DeletedUser(user));

        userRepository.delete(userRepository.findById(userName).get());
        userPassRepository.delete(userPassRepository.findById(userName).get());

        UserDTO userDTO = new UserDTO(user);

        return Optional.of(userDTO);
    }

    public Optional<UserDTO> getUserById(String username){

        Optional<User> wizard = userRepository.findById(username);
        return wizard.map(UserDTO::new);
    }

    public List<UserDTO> getAllUsers() {

        return userRepository.findAll().stream().map(UserDTO::new).toList();
    }

}
