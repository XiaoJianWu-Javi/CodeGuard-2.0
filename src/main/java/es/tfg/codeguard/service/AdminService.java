package es.tfg.codeguard.service;

import java.util.Optional;

import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.model.dto.UserPrivilegesDTO;

public interface AdminService {

    public abstract UserDTO deleteUser(String username);

    public abstract UserPassDTO updatePassword(String username, String newUserPass);

    public abstract UserDTO updateUserPrivileges(UserPrivilegesDTO userPrivilegesDTO);

}
