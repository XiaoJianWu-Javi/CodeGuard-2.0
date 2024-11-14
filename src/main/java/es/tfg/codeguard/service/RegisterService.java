package es.tfg.codeguard.service;

import es.tfg.codeguard.model.dto.JsonParserUserPassDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;

import java.util.Optional;

public interface RegisterService {

    public Optional<UserPassDTO> registerUser(JsonParserUserPassDTO jsonParserUserPassDTO);

}
