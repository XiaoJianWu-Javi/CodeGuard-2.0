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



@Service
public class JWTServiceImp implements JWTService {

    private final byte[] secretKeyByteArray;

    public JWTServiceImp (@Value("${jwt.secret-key}") String secretKey){

        this.secretKeyByteArray=secretKey.getBytes();

    }


    @Override
    public String createJwt(UserPassDTO userPass) {

        Date now = new Date();
        long expirationTime = 60*60*24;

        return JWT.create()
                .withSubject(userPass.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(new Date (now.getTime() + expirationTime))
                .sign(Algorithm.HMAC256(secretKeyByteArray));

    }

    @Override
    public UserPass extractUserPass(String jwt) {

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secretKeyByteArray)).build();

        DecodedJWT decodedjwt = jwtVerifier.verify(jwt);

        Claim claim = decodedjwt.getClaim("Subject");

        UserPass userPass = new UserPass();
        userPass.setUsername(claim.toString());
        userPass.setHashedPass("");
        userPass.setAdmin(false);

        return userPass;

    }

//    @Override
//    public Claims extractClaims(String jwt) throws UnsupportedJwtException, MalformedJwtException, ExpiredJwtException, IllegalArgumentException {
//        return Jwts.parser()
//                .setSigningKey(Keys.hmacShaKeyFor(secretKeyByteArray))
//                .build()
//                .parseClaimsJwt(jwt)
//                .getBody();
//    }
}
