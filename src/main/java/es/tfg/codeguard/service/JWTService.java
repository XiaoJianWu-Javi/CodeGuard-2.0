package es.tfg.codeguard.service;

import com.auth0.jwt.interfaces.Claim;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.model.entity.userpass.UserPass;

import org.springframework.stereotype.Service;

@Service
public interface JWTService {

    public String createJwt(UserPassDTO userPass);

    public UserPass extractUserPass(String jwt);

    //public Claim extractClaims(String jwt);

}
