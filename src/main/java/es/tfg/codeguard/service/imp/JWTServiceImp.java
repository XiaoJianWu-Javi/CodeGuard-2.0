package es.tfg.codeguard.service.imp;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.service.JWTService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class JWTServiceImp implements JWTService {

    private final String secretKey;

    public JWTServiceImp(@Value("${jwt.secret-key}") String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public String createJwt(UserPassDTO userPassDTO) {

        Date now = new Date();
        long expirationTime = 60 * 60 * 24 * 1000;

        return JWT.create()
                .withSubject(userPassDTO.username())
                .withIssuedAt(now)
                .withExpiresAt(new Date(now.getTime() + expirationTime))
                .sign(Algorithm.HMAC256(getSecretKeyBytes(this.secretKey)));

    }

    @Override
    public UserPass extractUserPass(String jwt) {

        Claim claim = getClaimsFromToken(jwt).get("sub");

        UserPass userPass = new UserPass();
        userPass.setUsername(claim.asString());
        userPass.setAdmin(false);

        return userPass;

    }

    @Override
    public Map<String, Claim> getClaimsFromToken(String jwt) {

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(getSecretKeyBytes(this.secretKey))).build();

        DecodedJWT decodedjwt = jwtVerifier.verify(jwt);

        return decodedjwt.getClaims();

    }

    private byte[] getSecretKeyBytes(String secretKey) {
        return secretKey.getBytes();
    }

}
