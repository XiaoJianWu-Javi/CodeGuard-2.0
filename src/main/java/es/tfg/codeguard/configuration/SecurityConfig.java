package es.tfg.codeguard.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import es.tfg.codeguard.service.imp.LoginServiceImp;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private LoginServiceImp loginService;

    @Bean
    public UserDetailsService userDetailsService() {
        return loginService;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder auth = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(loginService).passwordEncoder(passwordEncoder());
        return auth.build();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
            .authorizeHttpRequests(requests -> requests
            .requestMatchers("/login").permitAll()
            .anyRequest().authenticated()
        )
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(form -> form
            .loginPage("/login")
            .defaultSuccessUrl("/home")
            .permitAll()
        )
        .logout(LogoutConfigurer::permitAll);
        
        
        
        // .authorizeHttpRequests(
        //     authorize->authorize.requestMatchers("/**").permitAll()
        //             .anyRequest().authenticated()
        // ).csrf(csrf->csrf.disable())
        //         .headers(headers->headers.frameOptions(frameOptions->frameOptions.disable()));
        return httpSecurity.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Usar un encoder de contraseñas seguro
    }
}