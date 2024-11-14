package es.tfg.codeguard.service;

import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    //public abstract Optional<UserPassDTO> registerUser(String username, String password);

    public abstract Optional<UserDTO> deleteUser(String username);

    public abstract Optional<UserDTO> getUserById(String username);

    public List<UserDTO> getAllUsers();

}
