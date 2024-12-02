package es.tfg.codeguard.configuration;

import java.io.IOException;

import es.tfg.codeguard.model.repository.user.UserRepository;
import es.tfg.codeguard.service.LoginUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class JWTRequestFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoginUserDetailsService loginUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(AUTHORIZATION_HEADER);

        if (token != null) {

            UserPass userPass = jwtService.extractUserPass(token);

            if (userPass.getUsername().equals(userRepository.findById(userPass.getUsername()).get().getUsername())) {
                SecurityContextHolder.getContext()
                        .setAuthentication(new UsernamePasswordAuthenticationToken(loginUserDetailsService.loadUserByUsername(userPass.getUsername()), null, loginUserDetailsService.loadUserByUsername(userPass.getUsername()).getAuthorities()));

            }

        }
        filterChain.doFilter(request, response);
    }

}