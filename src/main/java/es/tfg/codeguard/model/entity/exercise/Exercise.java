package es.tfg.codeguard.model.entity.exercise;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import es.tfg.codeguard.util.ExerciseDescriptionNotValid;
import es.tfg.codeguard.util.ExerciseSolutionNotValidException;
import es.tfg.codeguard.util.ExerciseTitleNotValidException;
import es.tfg.codeguard.model.dto.ExerciseDTO;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "EXERCISE_SOLUTIONS",
    	      joinColumns = {@JoinColumn(name = "exercise_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "username")
    @Column(name = "solution")
    private Map<String, String> solutions;
    @Column(name = "compiler_class")
    private String compilerClass;
    @Lob
    private String placeholder;

    public Exercise() {}

    public Exercise(String id, String title, String description) {
        setId(id);
        setTitle(title);
        setDescription(description);
        setSolutions(new java.util.HashMap<>());
        setPlaceholder("");
    }

    public Exercise(ExerciseDTO exerciseDTO) {
        this(exerciseDTO.id(), exerciseDTO.title(), exerciseDTO.description());
        setTester(exerciseDTO.tester());
        setCreator(exerciseDTO.creator());
        setPlaceholder(exerciseDTO.placeholder());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        setCompilerClass();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank()) throw new ExerciseTitleNotValidException("Exercise title not valid [" +title +"]");
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.isBlank()) throw new ExerciseDescriptionNotValid("Exercise description not valid [" +description +"]");
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

    public String getCompilerClass() {
        return compilerClass;
    }

    private void setCompilerClass() {
    	this.compilerClass = Stream.of(this.getId().split("-"))
    								.map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
    								.collect(Collectors.joining());
    }

    public void addSolution(String username, String solution) {
        solutions.put(username, solution);
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    private void checkSolutions(Map<String, String> solutions) {
        if (solutions == null) throw new ExerciseSolutionNotValidException("Solution not valid [ null ]");
    	for (Map.Entry<String, String> solution : solutions.entrySet())
            if (solution == null || solution.getKey() == null || solution.getValue() == null) throw new ExerciseSolutionNotValidException("Solution not valid [ key: " +solution.getKey() +", value: " +solution.getValue() +" ]");
    }
}
