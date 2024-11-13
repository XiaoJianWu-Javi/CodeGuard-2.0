package es.tfg.codeguard.model.entity.deleteduser;

import es.tfg.codeguard.model.entity.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "DELETED_USER")
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