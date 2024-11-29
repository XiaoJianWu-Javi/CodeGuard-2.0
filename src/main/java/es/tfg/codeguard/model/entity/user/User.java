package es.tfg.codeguard.model.entity.user;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import jakarta.persistence.*;
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
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> exercises;

    public User() {
        setTester(false);
        setCreator(false);
        setExercises(new ArrayList<>());
    }

    public User(String username) {
        this();
        setUsername(username);
    }

    public User(String username, boolean tester, boolean creator) {
        this(username);
        setTester(tester);
        setCreator(creator);
    }

    public String getUsername() {
        if (username == null)
            throw new NoSuchElementException();
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.isBlank())
            throw new IllegalArgumentException();
        if (!username.matches(USERNAME_REGEXP))
            throw new IllegalArgumentException();
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

    public List<String> getExercises() {
        return new ArrayList<>(this.exercises);
    }

    public void setExercises(List<String> exercises) {
        checkExercises(exercises);
        this.exercises = new ArrayList<>(exercises);
    }

    private void checkExercises(List<String> exercises) {
        for (String exerciseID : exercises)
            if (exerciseID == null || exerciseID.isBlank()) throw new IllegalArgumentException();
    }

    public void addExercise(String exerciseId) {
        exercises.add(exerciseId);
    }
}
