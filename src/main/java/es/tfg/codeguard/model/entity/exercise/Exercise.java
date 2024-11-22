package es.tfg.codeguard.model.entity.exercise;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import es.tfg.codeguard.model.dto.ExerciseDTO;

@Entity
@Table(name = "EXERCISE")
public class Exercise {

    @Id
    private String id;
    @NotBlank
    private String title;
    @Lob @NotBlank
    private String description;

    private String creator;
    private String tester;

    @Lob
    private String test;
    @Lob @ElementCollection
    private List<String> solutions; //TODO: Add username column

    public Exercise() {}

    public Exercise(String id, String title, String description) {
        setId(id);
        setTitle(title);
        setDescription(description);
        setSolutions(new java.util.ArrayList<>());
    }

    public Exercise(ExerciseDTO exerciseDTO) {
        this(exerciseDTO.id(), exerciseDTO.title(), exerciseDTO.description());
        setTester(exerciseDTO.tester());
        setCreator(exerciseDTO.creator());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException();
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.isBlank()) throw new IllegalArgumentException();
        this.description = description;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getTester() {
        return tester;
    }

    public void setTester(String tester) {
        this.tester = tester;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public List<String> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<String> solutions) {
        this.solutions = solutions;
    }

    public void addSolution(String solution) {
        solutions.add(solution);
    }
}
