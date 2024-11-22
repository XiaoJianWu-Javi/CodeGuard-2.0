package es.tfg.codeguard.service.imp;

import java.util.Optional;

import es.tfg.codeguard.util.PasswordNotValidException;
import es.tfg.codeguard.util.UserNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.model.entity.deleteduser.DeletedUser;
import es.tfg.codeguard.model.entity.user.User;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.model.repository.deleteduser.DeletedUserRepository;
import es.tfg.codeguard.model.repository.user.UserRepository;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.service.AdminService;

@Service
public class AdminServiceImp implements AdminService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserPassRepository userPassRepository;
    @Autowired
    private DeletedUserRepository deletedUserRepository;

    @Override
    public UserDTO deleteUser(String username) {

        Optional<User> userOptional = userRepository.findById(username);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found [" +username +"]");
        }

        User user = userOptional.get();

        DeletedUser deletedUser = new DeletedUser(user);

        deletedUserRepository.save(deletedUser);

        userRepository.delete(user);

        return new UserDTO(deletedUser);
    }

    @Override
    //TODO: Actualizar para permitir cambiar cualquier campo del usuario menos el id
    public UserPassDTO updateUser(String username, String newUserPass) {

        Optional<User> userOptional = userRepository.findById(username);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found [" +username +"]");
        }

        UserPass userPass = userPassRepository.findById(username).get();

        try{
            userPass.setHashedPass(passwordEncoder.encode(newUserPass));
        }catch (PasswordNotValidException e){
            throw new PasswordNotValidException("Password not valid [" +newUserPass +"]");
        }

        userPassRepository.save(userPass);

        return new UserPassDTO(userPass);

    }

    @Override
    public UserDTO grantTester(String username) {

        Optional<User> userOptional = userRepository.findById(username);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found [" +username +"]");
        }

        User user = userOptional.get();
        user.setTester(true);

        userRepository.save(user);

        return new UserDTO(user);
    }

    @Override
    public UserDTO grantCreator(String username) {

        Optional<User> userOptional = userRepository.findById(username);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found [" +username +"]");
        }

        User user = userOptional.get();
        user.setCreator(true);

        userRepository.save(user);

        return new UserDTO(user);
    }

    @Override
    public UserDTO revokeTester(String username) {

        Optional<User> userOptional = userRepository.findById(username);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found [" +username +"]");
        }

        User user = userOptional.get();
        user.setTester(false);

        userRepository.save(user);

        return new UserDTO(user);
    }

    @Override
    public UserDTO revokeCreator(String username) {

        Optional<User> userOptional = userRepository.findById(username);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found [" +username +"]");
        }

        User user = userOptional.get();
        user.setCreator(false);

        userRepository.save(user);

        return new UserDTO(user);
    }

    @Override
    public UserDTO grantAllPrivileges(String username) {

        Optional<User> userOptional = userRepository.findById(username);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found [" +username +"]");
        }

        User user = userOptional.get();
        user.setTester(true);
        user.setCreator(true);

        userRepository.save(user);

        return new UserDTO(user);
    }

    @Override
    public UserDTO revokeAllPrivileges(String username) {

        Optional<User> userOptional = userRepository.findById(username);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found [" +username +"]");
        }

        User user = userOptional.get();
        user.setTester(false);
        user.setCreator(false);

        userRepository.save(user);

        return new UserDTO(user);
    }

}
