package es.tfg.codeguard.service;

import es.tfg.codeguard.model.dto.UserDTO;

import java.util.Optional;

public interface LoginService {

    public Optional<UserDTO> loginUser(String userName, String userPassword);

}
