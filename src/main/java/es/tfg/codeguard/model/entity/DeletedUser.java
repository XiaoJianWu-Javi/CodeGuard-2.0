package es.tfg.codeguard.model.entity;

import jakarta.persistence.Entity;

@Entity
public class DeletedUser extends User {

    public DeletedUser() {
    }

    public DeletedUser(User user) {
        super();
        setUsername(user.getUsername());
        setTester(user.isTester());
        setCreator(user.isCreator());
    }
}