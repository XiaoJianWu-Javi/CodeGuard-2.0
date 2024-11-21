package es.tfg.codeguard.service;

import java.util.List;
import java.util.Optional;

import es.tfg.codeguard.model.dto.UserDTO;

public interface UserService {

    public abstract Optional<UserDTO> deleteUser(String userToken);

    public abstract Optional<UserDTO> getUserById(String username);

    public List<UserDTO> getAllUsers();

}
