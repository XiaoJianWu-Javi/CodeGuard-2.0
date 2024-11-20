package es.tfg.codeguard.model.entity.user;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "USERS")
public class User {

    private static final String USERNAME_REGEXP = "^[a-zA-Z]{3,}\\w*$";
    
    @Id
    @NotBlank
    @Pattern(regexp = USERNAME_REGEXP, message = "{user.username.invalidPattern}")
    private String username;

    private Boolean tester;
    private Boolean creator;
    private List<Integer> exercises;

    public User(String username){
        this();
        setUsername(username);
    }

    public User() {
        setTester(false);
        setCreator(false);
        setExercises(new ArrayList<>());
    }

    public String getUsername() {
        if (username == null) throw new NoSuchElementException();
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.isBlank()) throw new IllegalArgumentException();
        if (!username.matches(USERNAME_REGEXP)) throw new IllegalArgumentException();
        this.username = username;
    }

    public Boolean isTester() {
        return tester;
    }

    public void setTester(Boolean tester) {
        this.tester = tester;
    }

    public Boolean isCreator() {
        return creator;
    }

    public void setCreator(Boolean creator) {
        this.creator = creator;
    }

   public List<Integer> getExercises() {
       return new ArrayList<>(this.exercises);
   }

   public void setExercises(List<Integer> exercises) {
       checkExercises(exercises);
       this.exercises = exercises;
   }

   private void checkExercises(List<Integer> exercises) {
       for (Integer exerciseID : exercises) if (exerciseID == null) throw new IllegalArgumentException();
   }
}
