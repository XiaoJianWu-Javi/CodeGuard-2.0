package es.tfg.codeguard.service.imp;

import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.model.entity.DeletedUser;
import es.tfg.codeguard.model.entity.User;
import es.tfg.codeguard.model.entity.UserPass;
import es.tfg.codeguard.model.repository.DeletedUserRepository;
import es.tfg.codeguard.model.repository.UserPassRepository;
import es.tfg.codeguard.model.repository.UserRepository;
import es.tfg.codeguard.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImp implements AdminService {

    //configurar las clases para cuando se tenga configurado la base de datos utilizar bcript desde config
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserPassRepository userPassRepository;
    @Autowired
    private DeletedUserRepository deletedUserRepository;



    public Optional<UserDTO> deleteUser(String userName) {


        if(userPassRepository.findById(userName).isEmpty()){
            return Optional.empty();
        }

        User user = userRepository.findById(userName).get();



        deletedUserRepository.save(new DeletedUser(user));

        userRepository.delete(userRepository.findById(userName).get());
        userPassRepository.delete(userPassRepository.findById(userName).get());



        return Optional.of(new UserDTO(user));
    }

    //Actualizar para permitir cambiar cualquier campo del wizard menos el id
    public Optional<UserPassDTO> updateUser(String userName, String newUserPass){

        if(userPassRepository.findById(userName).isEmpty()){
            return Optional.empty();
        }

        UserPass newUser = userPassRepository.findById(userName).get();

        newUser.setHashedPass(passwordEncoder.encode(newUserPass));

        userPassRepository.save(newUser);

        UserPassDTO userPassDTO = new UserPassDTO(newUser);

        return Optional.of(userPassDTO);

    }

    public Optional<UserDTO> grantTester(String userName){

        if(userRepository.findById(userName).isEmpty()){
            return Optional.empty();
        }

        User user = userRepository.findById(userName).get();

        user.setTester(true);
        userRepository.save(user);

        UserDTO userDTO = new UserDTO(user);

        return Optional.of(userDTO);
    }

    public Optional<UserDTO> grantCreator(String userName){

        if(userRepository.findById(userName).isEmpty()){
            return Optional.empty();
        }

        User user = userRepository.findById(userName).get();

        user.setCreator(true);
        userRepository.save(user);

        UserDTO userDTO = new UserDTO(user);

        return Optional.of(userDTO);
    }

    public Optional<UserDTO> revokeTester(String userName){

        if(userRepository.findById(userName).isEmpty()){
            return Optional.empty();
        }

        User user = userRepository.findById(userName).get();

        user.setTester(false);
        userRepository.save(user);

        UserDTO userDTO = new UserDTO(user);

        return Optional.of(userDTO);
    }

    public Optional<UserDTO> revokeCreator(String userName){

        if(userRepository.findById(userName).isEmpty()){
            return Optional.empty();
        }

        User user = userRepository.findById(userName).get();

        user.setCreator(false);
        userRepository.save(user);

        UserDTO userDTO = new UserDTO(user);

        return Optional.of(userDTO);
    }

    public Optional<UserDTO> grantAllPrivileges(String userName){

        if(userRepository.findById(userName).isEmpty()){
            return Optional.empty();
        }

        User user = userRepository.findById(userName).get();
        //cuando este implementada la base de datos llamar a los 2 metodos que cambian los permisos y pedir el optional a la base de datos en el return
        user.setTester(true);
        user.setCreator(true);
        userRepository.save(user);

        UserDTO userDTO = new UserDTO(user);

        return Optional.of(userDTO);
    }

    public Optional<UserDTO> revokeAllPrivileges(String userName){

        if(userRepository.findById(userName).isEmpty()){
            return Optional.empty();
        }

        User user = userRepository.findById(userName).get();
        //cuando este implementada la base de datos llamar a los 2 metodos que cambian los permisos y pedir el optional a la base de datos en el return
        user.setTester(false);
        user.setCreator(false);
        userRepository.save(user);

        UserDTO userDTO = new UserDTO(user);

        return Optional.of(userDTO);
    }



}
