package es.tfg.codeguard.service.imp;

import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.service.LoginService;

import java.util.Optional;

public class LoginServiceImp implements LoginService {

    @Override
    public Optional<UserDTO> loginUser(String userName, String userPassword) {
        return Optional.empty();
    }
}
