package es.tfg.codeguard.service;

import java.util.List;
import java.util.Optional;

import es.tfg.codeguard.model.dto.ChangePasswordDTO;
import es.tfg.codeguard.model.dto.UserDTO;

public interface UserService {

    public abstract UserDTO deleteUser(String userToken);
    
    public abstract UserDTO restoreUser(String username, String password);

    public abstract UserDTO getUserById(String username);

    public List<UserDTO> getAllUsers();

    public UserDTO changePassword(String userToken, ChangePasswordDTO changePasswordDTO);

}
