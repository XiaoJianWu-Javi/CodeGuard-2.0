package es.tfg.codeguard.model.dto;

import java.util.List;

import es.tfg.codeguard.model.entity.deleteduser.DeletedUser;
import es.tfg.codeguard.model.entity.user.User;

public record UserDTO(String username, boolean tester, boolean creator, List<Integer> exercises) {

    public UserDTO(User user) {
        this(user.getUsername(), user.isTester(), user.isCreator(), user.getExercises());
    }

    public UserDTO(DeletedUser deletedUser) {
    	this(deletedUser.getUsername(), deletedUser.isTester(), deletedUser.isCreator(), deletedUser.getExercises());
    }
}
