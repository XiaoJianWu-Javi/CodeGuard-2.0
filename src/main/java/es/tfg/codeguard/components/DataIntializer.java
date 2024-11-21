package es.tfg.codeguard.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
        firstExercise.setCreator("Goldbach"); firstExercise.setTester("Fermat");
        exerciseRepository.save(firstExercise);
    }

    //TODO: Eliminar cuando la aplicación suba a producción
    @Bean
    void dataInizializerForFrontTesting() {
        //Users
        User userTester = new User("Merlin", true, false);
        UserPass userTesterPass = new UserPass("Merlin",
                                                passwordEncoder.encode("merlin"), 
                                                false);
        userRepository.save(userTester);
        userPassRepository.save(userTesterPass);
        User userCreator = new User("Gandalf", false, true);
        UserPass userCreatorPass = new UserPass("Gandalf",
                                                passwordEncoder.encode("gandalf"), 
                                                false);
        userRepository.save(userCreator);
        userPassRepository.save(userCreatorPass);
        User userNormal = new User("Dumbledore", false, false);
        UserPass userNormalPass = new UserPass("Dumbledore",
                                                passwordEncoder.encode("dumbledore"), 
                                                false);
        userRepository.save(userNormal);
        userPassRepository.save(userNormalPass);
        User userTryhard = new User("Harry", false, false);
        userTryhard.setExercises(new java.util.ArrayList<>(){{ add("goldbach"); add("gandalf-1");}});
        UserPass userTryhardPass = new UserPass("Harry",
                                                passwordEncoder.encode("potter"), 
                                                false);
        userRepository.save(userTryhard);
        userPassRepository.save(userTryhardPass);

        //Exercises
        Exercise gandalfExercise = new Exercise("gandalf-1", "La Prueba", "Un mago nunca llega tarde.");
        gandalfExercise.setCreator("gandalf"); gandalfExercise.setTester("Saruman");
        exerciseRepository.save(gandalfExercise);

        Exercise sarumanExercise = new Exercise("saruman-123", "La Trainción de Isengard", "Si el orgullo es doble... doble es la caída");
        sarumanExercise.setCreator("Saruman"); sarumanExercise.setTester("Saruman");
    }

}
