package es.tfg.codeguard.service.imp;

import es.tfg.codeguard.model.entity.user.User;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.service.JWTService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Service
public class JWTServiceImp implements JWTService {


    //private static final String SIGNING_KEY="nuhman5555";

    // <<-FIELDS->>
    private final byte[] secretKeyByteArray;

    // <<-CONSTRUCTOR->>
    @Autowired
    public JWTServiceImp(
            @Value("${jwt.secret-key}")
            String secretKey
    ) {
        secretKeyByteArray = secretKey.getBytes();
    }


    @Override
    public UserPass extractUser(String jwt) {

        UserPass userPass = new UserPass();
        userPass.setUsername(extractClaims(jwt).getSubject());
        userPass.setHashedPass("");
        userPass.setAdmin(false);

        return userPass;
    }

    @Override
    public String createJwt(UserPass userPass) {

        Date now = new Date();
        long expirationTime = 60*60*24;


        return Jwts.builder()
                .setSubject(userPass.getUsername())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() +expirationTime))
                .signWith(Keys.hmacShaKeyFor(secretKeyByteArray))
                .compact();

    }

    @Override
    public Claims extractClaims(String jwt)
            throws UnsupportedJwtException,
            MalformedJwtException,
            ExpiredJwtException,
            IllegalArgumentException
    {
        return Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(secretKeyByteArray))
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }


}
