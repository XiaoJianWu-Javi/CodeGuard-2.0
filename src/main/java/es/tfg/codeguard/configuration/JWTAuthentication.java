package es.tfg.codeguard.configuration;

import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTAuthentication extends OncePerRequestFilter {

    private static final String TOKEN_PREFIX="Bearer ";

    private static final String HEADER_STRING="Authorization";


    // <<-FIELD->>
    private JWTService jwtService;

    // <<-CONSTRUCTOR->>
    @Autowired
    public JWTAuthentication(JWTService jwtService) {
        this.jwtService = jwtService;
    }


    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(HEADER_STRING);
        String authToken = null;
        String username = null;

        if(header!=null && header.startsWith(TOKEN_PREFIX)){

            authToken = header.substring(TOKEN_PREFIX.length());

            UserPass userPass = jwtService.extractUser(authToken);

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userPass, null, null));

        }

        filterChain.doFilter(request,response);

    }
}
