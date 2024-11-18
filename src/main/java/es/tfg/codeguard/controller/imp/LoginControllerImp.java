package es.tfg.codeguard.controller.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import es.tfg.codeguard.model.dto.JsonParserUserPassDTO;
import es.tfg.codeguard.service.LoginUserDetailsService;


@Controller
public class LoginControllerImp {
    private boolean death = true;

    @Autowired
    private LoginUserDetailsService userDetailsService;

    @PostMapping("/logiin")
    public String postMethodName(@RequestBody JsonParserUserPassDTO jsonParserUserPassDTO) {
        // UserDetails principal = userDetailsService.loadUserByUsername(jsonParserUserPassDTO.getUsername());
        // Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
        // SecurityContext context = SecurityContextHolder.createEmptyContext();
        // context.setAuthentication(authentication);
        if (death) throw new IllegalArgumentException("MUERETE");
        return "index.html";
    }
    
}
