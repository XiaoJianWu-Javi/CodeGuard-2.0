package es.tfg.codeguard.configuration;

import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class JWTRequestFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final JWTService jwtService;

    private final UserPassRepository userPassRepository;

    @Autowired
    public JWTRequestFilter(JWTService jwtService, UserPassRepository userPassRepository) {
        this.jwtService = jwtService;
        this.userPassRepository = userPassRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(AUTHORIZATION_HEADER);

        if (token != null) {

            UserPass userPass = jwtService.extractUserPass(token);

            if (userPass.getUsername().equals(userPassRepository.findByUsername(userPass.getUsername()).get().getUsername())) {
                SecurityContextHolder.getContext()
                        .setAuthentication(new UsernamePasswordAuthenticationToken(userPass, null, null));

            }

        }
        filterChain.doFilter(request, response);
    }
}


