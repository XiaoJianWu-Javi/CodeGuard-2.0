package es.tfg.codeguard.service.imp;

import java.util.Optional;

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
    public Optional<UserDTO> deleteUser(String username) {

        if (userPassRepository.findById(username).isEmpty()) {
            return Optional.empty();
        }

        DeletedUser user = new DeletedUser(userRepository.findById(username).get());

        deletedUserRepository.save(user);

        userRepository.delete(userRepository.findById(username).get());

        return Optional.of(new UserDTO(user));
    }

    @Override
    //TODO: Actualizar para permitir cambiar cualquier campo del usuario menos el id
    public Optional<UserPassDTO> updateUser(String username, String newUserPass) {

        if (userPassRepository.findById(username).isEmpty()) {
            return Optional.empty();
        }

        UserPass newUser = userPassRepository.findById(username).get();

        newUser.setHashedPass(passwordEncoder.encode(newUserPass));

        userPassRepository.save(newUser);

        UserPassDTO userPassDTO = new UserPassDTO(newUser);

        return Optional.of(userPassDTO);

    }

    @Override
    public Optional<UserDTO> grantTester(String username) {

        if (userRepository.findById(username).isEmpty()) {
            return Optional.empty();
        }

        User user = userRepository.findById(username).get();

        user.setTester(true);
        userRepository.save(user);

        UserDTO userDTO = new UserDTO(user);

        return Optional.of(userDTO);
    }

    @Override
    public Optional<UserDTO> grantCreator(String username) {

        if (userRepository.findById(username).isEmpty()) {
            return Optional.empty();
        }

        User user = userRepository.findById(username).get();

        user.setCreator(true);
        userRepository.save(user);

        UserDTO userDTO = new UserDTO(user);

        return Optional.of(userDTO);
    }

    @Override
    public Optional<UserDTO> revokeTester(String username) {

        if (userRepository.findById(username).isEmpty()) {
            return Optional.empty();
        }

        User user = userRepository.findById(username).get();

        user.setTester(false);
        userRepository.save(user);

        UserDTO userDTO = new UserDTO(user);

        return Optional.of(userDTO);
    }

    @Override
    public Optional<UserDTO> revokeCreator(String username) {

        if (userRepository.findById(username).isEmpty()) {
            return Optional.empty();
        }

        User user = userRepository.findById(username).get();

        user.setCreator(false);
        userRepository.save(user);

        UserDTO userDTO = new UserDTO(user);

        return Optional.of(userDTO);
    }

    @Override
    public Optional<UserDTO> grantAllPrivileges(String username) {

        if (userRepository.findById(username).isEmpty()) {
            return Optional.empty();
        }

        User user = userRepository.findById(username).get();
        user.setTester(true);
        user.setCreator(true);
        userRepository.save(user);

        return Optional.of(new UserDTO(user));
    }

    @Override
    public Optional<UserDTO> revokeAllPrivileges(String username) {

        if (userRepository.findById(username).isEmpty()) {
            return Optional.empty();
        }

        User user = userRepository.findById(username).get();
        user.setTester(false);
        user.setCreator(false);
        userRepository.save(user);

        return Optional.of(new UserDTO(user));
    }

}
