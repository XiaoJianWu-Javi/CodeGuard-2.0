package es.tfg.codeguard.components;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import es.tfg.codeguard.model.entity.exercise.Exercise;
import es.tfg.codeguard.model.entity.user.User;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.model.repository.exercise.ExerciseRepository;
import es.tfg.codeguard.model.repository.user.UserRepository;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;

@Component
public class DataIntializer {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserPassRepository userPassRepository;
    @Autowired
    private ExerciseRepository exerciseRepository; //TODO: utilizar el servicio cuando esté implementado

    @Bean
    void firstAdmin() {
        User firstAdmin = new User("Saruman", true, true);
        UserPass firstAdminPass = new UserPass("Saruman", 
                                                passwordEncoder.encode("s4rum4n"), 
                                                true);

        userRepository.save(firstAdmin);
        userPassRepository.save(firstAdminPass);
    }

    @Bean
    void firstExercise() {
        Exercise firstExercise = new Exercise("goldbach", "La Conjetura de Goldbach", "Todo número par mayor que 2 puede escribirse como suma de dos números primos.");
        firstExercise.setCreator("Goldbach");
        exerciseRepository.save(firstExercise);
    }

}
