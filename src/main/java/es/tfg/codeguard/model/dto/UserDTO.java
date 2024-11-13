package es.tfg.codeguard.model.dto;

import es.tfg.codeguard.model.entity.deleteduser.DeletedUser;
import es.tfg.codeguard.model.entity.user.User;

//TODO: Make Exercise list implementation when exercises are done
public class UserDTO {
	
    private String username;
    private Boolean tester;
    private Boolean creator;
//  private List<Exercise> exercises;

    public UserDTO(){
    }

    public UserDTO(User user) {
        setUsername(user.getUsername());
        setTester(user.isTester());
        setCreator(user.isCreator());
//      setExercises(user.getExercises());
    }

    public UserDTO(DeletedUser deletedUser) {
    	setUsername(deletedUser.getUsername());
        setTester(deletedUser.isTester());
        setCreator(deletedUser.isCreator());
//      setExercises(deletedUser.getSpells());
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isTester() {
        return tester;
    }

    public void setTester(boolean tester) {
        this.tester = tester;
    }

    public boolean isCreator() {
        return creator;
    }

    public void setCreator(boolean creator) {
        this.creator = creator;
    }

//    public List<Exercise> getExercises() {
//        return new ArrayList<>(exercises);
//    }
//
//    public void setExercises(List<Exercise> exercises) {
//        this.exercises = exercises;
//    }
}
