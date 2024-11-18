package es.tfg.codeguard.service;

import es.tfg.codeguard.model.entity.user.User;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import io.jsonwebtoken.Claims;
import org.apache.el.lang.FunctionMapperImpl;
import org.hibernate.boot.archive.scan.internal.ClassDescriptorImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public interface JWTService {

    public UserPass extractUser(String jwt);

    public String createJwt (UserPass userPass);

    public boolean validateToken(String authToken, UserDetails userDetails);

    public Claims extractClaims(String jwt);

}
