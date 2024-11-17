package es.tfg.codeguard.model.dto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Date;

public class JWTUtilToken {

    public String generateToken(String username){

        Claims claims = (Claims) Jwts.claims().setSubject(username);
        claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));

        return Jwts.builder().setClaims(claims).setIssuer("http://nuhman").setIssuedAt(new Date(System.currentTimeMillis())).compact();

    }

}
