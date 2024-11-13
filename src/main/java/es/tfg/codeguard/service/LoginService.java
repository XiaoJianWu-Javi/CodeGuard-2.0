package es.tfg.codeguard.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import es.tfg.codeguard.model.dto.UserDTO;

@Service
public interface LoginService extends UserDetailsService {

    public Optional<UserDTO> loginUser(String userName, String userPassword);
}
