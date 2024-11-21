package es.tfg.codeguard.service;

import java.util.Optional;

import es.tfg.codeguard.model.dto.AuthDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;

public interface RegisterService {

    public Optional<UserPassDTO> registerUser(AuthDTO authDTO);

}
