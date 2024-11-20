package es.tfg.codeguard.service;

import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;

import java.util.Optional;

public interface AdminService {

    public abstract Optional<UserDTO> deleteUser(String userName);

    public abstract Optional<UserPassDTO> updateUser(String userName, String newUserPass);

    public abstract Optional<UserDTO> grantTester(String userName);

    public abstract Optional<UserDTO> grantCreator(String userName);

    public abstract Optional<UserDTO> revokeTester(String userName);

    public abstract Optional<UserDTO> revokeCreator(String userName);

    public abstract Optional<UserDTO> grantAllPrivileges(String userName);

    public abstract Optional<UserDTO> revokeAllPrivileges(String userName);





}
