package es.tfg.codeguard.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface LoginService extends UserDetailsService {
}
