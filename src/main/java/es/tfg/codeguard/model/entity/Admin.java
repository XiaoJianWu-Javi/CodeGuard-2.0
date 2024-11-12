package es.tfg.codeguard.model.entity;

import es.tfg.codeguard.model.entity.user.User;

public class Admin extends User {

    public Admin(String username){
        super(username);
        setTester(true);
        setCreator(true);
    }

}
