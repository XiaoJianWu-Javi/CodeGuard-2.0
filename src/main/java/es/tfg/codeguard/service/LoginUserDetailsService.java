package es.tfg.codeguard.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface LoginUserDetailsService extends UserDetailsService {

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
