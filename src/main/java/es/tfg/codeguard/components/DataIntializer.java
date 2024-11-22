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

    @Bean
    void compileExercise() {
        Exercise firstExercise = new Exercise("plural", "Plural",
                "We need a simple function that determines if a plural is needed or not. It should take a number, and return true if a plural should be used with that number or false if not. This would be useful when printing out a string such as 5 minutes, 14 apples, or 1 sun.\n" +
                        "\n" +
                        "You only need to worry about english grammar rules for this kata, where anything that isn't singular (one of something), it is plural (not one of something).\n" +
                        "\n" +
                        "All values will be positive integers or floats, or zero.");
        firstExercise.setCreator("Saruman");
        firstExercise.setTester("Saruman");
        firstExercise.setTest("" +
                "import org.junit.jupiter.api.Test;\n" +
                "import static org.junit.jupiter.api.Assertions.assertEquals;\n" +
                "\n" +
                "\n" +
                "public class PluralTest {\n" +
                "    @Test\n" +
                "    public void BasicTest() {\n" +
                "      assertEquals(true,Plural.isPlural(0f));\n" +
                "      assertEquals(true,Plural.isPlural(0.5f));\n" +
                "      assertEquals(false,Plural.isPlural(1f));\n" +
                "      assertEquals(true,Plural.isPlural(100f));\n" +
                "    }\n" +
                "}");
        exerciseRepository.save(firstExercise);
    }

}
