package es.tfg.codeguard.model.entity.exercise;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import es.tfg.codeguard.model.dto.ExerciseDTO;
import es.tfg.codeguard.model.entity.user.User;
import es.tfg.codeguard.util.ExerciseDescriptionNotValid;
import es.tfg.codeguard.util.ExerciseSolutionNotValidException;
import es.tfg.codeguard.util.ExerciseTitleNotValidException;
import jakarta.persistence.*;
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
    @Lob
    private String placeholder;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "EXERCISE_TRIED_USERS",
            joinColumns = @JoinColumn(name = "exercise_id"))
    @Column(name = "username")
    private Set<String> triedUsernames;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "EXERCISE_SOLVED_USERS",
            joinColumns = @JoinColumn(name = "exercise_id"))
    @Column(name = "username")
    private Set<String> solvedUsernames;
    @Column(name = "solution_percentage")
    private Integer solutionPercentage;

    public Exercise() {}

    public Exercise(String id, String title, String description) {
        setId(id);
        setTitle(title);
        setDescription(description);
        setSolutions(new java.util.HashMap<>());
        setPlaceholder("");
        setTriedUsernames(new HashSet<>());
        setSolvedUsernames(new HashSet<>());
        setSolutionPercentage(0);
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
        this.solutions = new java.util.HashMap<>(solutions);
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

    public Integer getSolvedTimes() {
        return getSolutions().size();
    }

    public void addTriedUsername(String username) {
        triedUsernames.add(username);
        updateSolutionPercentage();
    }

    public void removeTriedUsername(String username) {
        triedUsernames.remove(username);
    }

    public Set<String> getTriedUsernames() {
        return new HashSet<>(triedUsernames);
    }

    public void setTriedUsernames(Set<String> triedUsernames) {
        this.triedUsernames = triedUsernames;
    }

    public void addSolvedUsername(String username) {
        solvedUsernames.add(username);
        updateSolutionPercentage();
    }

    public void removeSolvedUsername(String username) {
        solvedUsernames.remove(username);
    }

    public Set<String> getSolvedUsernames() {
        return new HashSet<>(solvedUsernames);
    }

    public void setSolvedUsernames(Set<String> solvedUsernames) {
        this.solvedUsernames = solvedUsernames;
    }

    public Integer getSolutionPercentage() {
        return this.solutionPercentage;
    }

    public void setSolutionPercentage(Integer solutionPercentage) {
        this.solutionPercentage = solutionPercentage;
    }

    private void updateSolutionPercentage() {
        if (!this.getTriedUsernames().isEmpty()) {
            this.solutionPercentage = this.getSolvedUsernames().size() * 100 / this.getTriedUsernames().size();
        }
    }

    private void checkSolutions(Map<String, String> solutions) {
        if (solutions == null) throw new ExerciseSolutionNotValidException("Solution not valid [ null ]");
    	for (Map.Entry<String, String> solution : solutions.entrySet())
            if (solution == null || solution.getKey() == null || solution.getValue() == null) throw new ExerciseSolutionNotValidException("Solution not valid [ key: " +solution.getKey() +", value: " +solution.getValue() +" ]");
    }
}
