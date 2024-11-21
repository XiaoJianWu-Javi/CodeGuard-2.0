package es.tfg.codeguard.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import es.tfg.codeguard.model.dto.UserPassDTO;

@Service
public interface LoginService {

    public Optional<UserPassDTO> loginUser(String userName, String userPassword);
}
