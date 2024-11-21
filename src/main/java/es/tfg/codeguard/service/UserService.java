package es.tfg.codeguard.service;

import es.tfg.codeguard.model.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public abstract Optional<UserDTO> deleteUser(String userToken);

    public abstract Optional<UserDTO> getUserById(String username);

    public List<UserDTO> getAllUsers();

}
