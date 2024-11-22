package es.tfg.codeguard.model.entity.exercise;

import java.util.Map;

import es.tfg.codeguard.model.dto.ExerciseDTO;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

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
    @Lob
    @ElementCollection
    @CollectionTable(name = "EXERCISE_SOLUTIONS", 
    	      joinColumns = {@JoinColumn(name = "exercise_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "username")
    @Column(name = "solution")
    private Map<String, String> solutions;

    public Exercise() {}

    public Exercise(String id, String title, String description) {
        setId(id);
        setTitle(title);
        setDescription(description);
        setSolutions(new java.util.HashMap<>());
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

    public Map<String, String> getSolutions() {
        return new java.util.HashMap<>(solutions);
    }

    public void setSolutions(Map<String, String> solutions) {
        checkSolutions(solutions);
        this.solutions = solutions;
    }

    public void addSolution(String username, String solution) {
        solutions.put(username, solution);
    }
    
    private void checkSolutions(Map<String, String> solutions) {
    	for (Map.Entry<String, String> solution : solutions.entrySet()) 
            if (solution == null) throw new IllegalArgumentException();
    }
}
