package es.tfg.codeguard.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/admin/**").hasRole("ADMIN")  // Solo los usuarios con rol ADMIN pueden acceder a /admin/**
                        .anyRequest().authenticated()  // Cualquier otra solicitud requiere autenticación
                )
                .formLogin((form) -> form  // Activar login por formulario
                        .permitAll()  // Permite el acceso al formulario de login sin autenticación
                )
                .logout((logout) -> logout  // Permitir logout
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Crear usuario con rol de ADMIN y acceso completo
        User.UserBuilder users = User.withUsername("admin");
        users.password(passwordEncoder().encode("admin123")); // Establecer una contraseña cifrada
        users.roles("ADMIN"); // Rol ADMIN

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.build());
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Usar un encoder de contraseñas seguro
    }
}