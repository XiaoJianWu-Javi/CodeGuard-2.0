package es.tfg.codeguard.service;

import java.util.Optional;

import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;

public interface AdminService {

    public abstract UserDTO deleteUser(String username);

    public abstract UserPassDTO updateUser(String username, String newUserPass);

    public abstract UserDTO grantTester(String username);

    public abstract UserDTO grantCreator(String username);

    public abstract UserDTO revokeTester(String username);

    public abstract UserDTO revokeCreator(String username);

    public abstract UserDTO grantAllPrivileges(String username);

    public abstract UserDTO revokeAllPrivileges(String username);

}
