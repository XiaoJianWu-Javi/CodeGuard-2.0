package es.tfg.codeguard.service.imp;

import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.service.LoginService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImp implements LoginService {

    @Override
    public Optional<UserDTO> loginUser(String userName, String userPassword) {
        return Optional.empty();
    }
}
