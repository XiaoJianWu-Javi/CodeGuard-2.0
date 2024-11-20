package es.tfg.codeguard.service;

import com.auth0.jwt.interfaces.Claim;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface JWTService {

    public String createJwt(UserPassDTO userPass);

    public UserPass extractUserPass(String jwt);

    public Map<String, Claim> getClaimsFromToken(String jwt);
}
