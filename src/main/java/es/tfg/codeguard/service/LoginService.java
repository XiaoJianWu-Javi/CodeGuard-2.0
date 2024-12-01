package es.tfg.codeguard.service;

import es.tfg.codeguard.model.dto.AuthDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {

    public UserPassDTO loginUser(AuthDTO authDTO);
}
