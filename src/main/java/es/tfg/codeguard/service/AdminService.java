package es.tfg.codeguard.service;

import java.util.Optional;

import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;

public interface AdminService {

    public abstract Optional<UserDTO> deleteUser(String username);

    public abstract Optional<UserPassDTO> updateUser(String username, String newUserPass);

    public abstract Optional<UserDTO> grantTester(String username);

    public abstract Optional<UserDTO> grantCreator(String username);

    public abstract Optional<UserDTO> revokeTester(String username);

    public abstract Optional<UserDTO> revokeCreator(String username);

    public abstract Optional<UserDTO> grantAllPrivileges(String username);

    public abstract Optional<UserDTO> revokeAllPrivileges(String username);

}
