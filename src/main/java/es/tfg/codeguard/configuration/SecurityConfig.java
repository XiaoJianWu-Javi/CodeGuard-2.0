package es.tfg.codeguard.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JWTRequestFilter jwtRequestFilter;

    @Autowired
    public SecurityConfig(JWTRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }


//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//
//        return httpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
//                //.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class) //CAMBIADO
//                .cors(cors->cors.configurationSource(request -> {
//                    CorsConfiguration configuration = new CorsConfiguration();
//                    configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
//                    configuration.setAllowedMethods(Arrays.asList("*"));
//                    configuration.setAllowedHeaders(Arrays.asList("*"));
//                    return configuration;
//                }))
//                .authorizeHttpRequests(registry -> {
//                    registry.requestMatchers("/home", "/register", "/api/**").permitAll(); //AÑADIDO LOGIN
//                    registry.requestMatchers("/admin/**", "/h2-console/**").hasRole("ADMIN");
//                    registry.requestMatchers("/user/**").hasRole("USER");
//                    registry.anyRequest().authenticated();
//                })
//                //OAUTH2
//                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.loginProcessingUrl("/login"))
//                .headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
//                .build();
//    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(registry -> {
                    //registry.requestMatchers("/login","/register").permitAll();
                    registry.anyRequest().permitAll();
                }).build();


    }

//    @Bean
//    UserDetailsService userDetailsService() {
//        return userDetailsService;
//    }

//    @Bean
//    AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userDetailsService);
//        provider.setPasswordEncoder(passwordEncoder());
//        return provider;
//    }

//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();  // Usar un encoder de contraseñas seguro
//    }

    @Bean
    PasswordEncoder passwordEncoder(@Value("${jwt.secret-key}") String secretKey) {

        //return new Pbkdf2PasswordEncoder(secretKey, 16, 31000, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);

        return new BCryptPasswordEncoder();

    }

}