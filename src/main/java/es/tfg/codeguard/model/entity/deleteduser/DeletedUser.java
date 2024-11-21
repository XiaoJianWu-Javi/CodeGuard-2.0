package es.tfg.codeguard.model.entity.deleteduser;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import es.tfg.codeguard.model.entity.user.User;

@Entity
@Table(name = "DELETED_USER")
public class DeletedUser extends User {

    public DeletedUser() {}

    public DeletedUser(User user) {
        super();
        setUsername(user.getUsername());
        setTester(user.isTester());
        setCreator(user.isCreator());
        setExercises(user.getExercises());
    }
}