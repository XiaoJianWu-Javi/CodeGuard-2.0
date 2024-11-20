package es.tfg.codeguard.components;

import es.tfg.codeguard.model.entity.exercise.Exercise;
import es.tfg.codeguard.model.entity.user.User;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.model.repository.exercise.ExerciseRepository;
import es.tfg.codeguard.model.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataIntializer {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPassRepository userPassRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Bean
    void firstAdmin() {
        User firstAdmin = new User("Saruman");
        firstAdmin.setTester(true);
        firstAdmin.setCreator(true);

        UserPass firstAdminPass = new UserPass();
        firstAdminPass.setUsername("Saruman");
        firstAdminPass.setAdmin(true);
        firstAdminPass.setHashedPass(passwordEncoder.encode("s4rum4n"));

        userRepository.save(firstAdmin);
        userPassRepository.save(firstAdminPass);
    }

    @Bean
    void firstExercises() {

        String bigDescription = """
                                Introduction\r
                                You are on a party with your friends and one of them suggest to play a game called "Magic Music Box". The game consists of a magic music box that is playing one by one all the music notes in order from DO to SI over and over. The goal of the game is to store in the magic music box words that contain the musical note that is being played at each moment.\r
                                Music Box\r
                                Task\r
                                In this kata you have to create a function that given an array of words returns another array with all the words that have been stored in the magic music box in the correct order.\r
                                A word can be stored in the magic music box when it contains the musical note that the box is playing at each moment. When a word is stored, the music box starts to play the next note, and so on.\r
                                The function must try to store every word from the input if possible, even if it means to retry some words that didn't fitted previuosly.\r
                                If there are no more words in the input that can be stored in the box, the function should stop and return the array with the stored words in the order they have been stored.\r
                                Rules\r
                                The same word cannot be stored more than once.\r
                                The magic music box plays the musical notes over and over, in a cyclic infinite loop.\r
                                If a word cannot be stored, it does not mean it could not be stored in the future with the appropiate note.\r
                                You don't have to verify the word, you only have to check that it contains the musical note with all its letters together (i.e. SOLAR would be a valid word but SOCIAL wouldn't).\r
                                The musical notes are represented in the european solf\u00e8ge format (DO, RE, MI, FA, SOL, LA, SI).\r
                                The method must return an empty array if there are no words present inside the array.\r
                                Example\r
                                Given the input array ["DOWN","PLANE","AMIDST","REPTILE","SOFA","SOLAR","SILENCE","DOWN","MARKDOWN"]\r
                                The function flow should be:\r
                                As the first musical note is DO, the word DOWN fits, and is stored inside the box.\r
                                The next note is RE, and iterating the array, the next word that fits is REPTILE.\r
                                The next note is MI, but if we continue in the array, we don't find any word that fits, so we should try again from the begining. This time, we find AMIDST, which fits.\r
                                The flow continues like this for the next musical notes (FA, SOL, LA, SI). At this point, our temporal resulted array looks like this: ["DOWN", "REPTILE", "AMIDST", "SOFA", "SOLAR", "PLANE", "SILENCE"]\r
                                The next note is DO again, because the music box never stops playing notes. Following the array, we find the word DOWN. The word itself fits with the note, but as long as it is forbidden to repeat words, we have to omit it. The next word that fits is MARKDOWN, we store it and continue.\r
                                The next note is RE, but this time, searching a fitting word, we end doing a complete iteration over the array with finding any, so the function ends and return the definitive array solution: ["DOWN","REPTILE","AMIDST","SOFA","SOLAR","PLANE","SILENCE","MARKDOWN"]""";
        Exercise firstExercise = new Exercise("Magic Music Box", bigDescription);
        firstExercise.setCreator("Saruman");
        exerciseRepository.save(firstExercise);

        Exercise secondExercise = new Exercise("La Conjetura de Goldbach", "Todo número par mayor que 2 puede escribirse como suma de dos números primos.");
        secondExercise.setCreator("Saruman");
        exerciseRepository.save(secondExercise);
    }

}
