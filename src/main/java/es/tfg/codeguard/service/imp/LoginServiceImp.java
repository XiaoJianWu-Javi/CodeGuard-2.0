package es.tfg.codeguard.service.imp;

import java.util.Optional;

import org.springframework.stereotype.Service;

import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.service.LoginService;

@Service
public class LoginServiceImp implements LoginService {

    @Override
    public Optional<UserDTO> loginUser(String username, String userPassword) {
        return Optional.empty();
    }
}
