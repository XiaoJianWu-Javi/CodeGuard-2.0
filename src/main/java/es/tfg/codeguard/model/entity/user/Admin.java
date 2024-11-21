package es.tfg.codeguard.model.entity.user;

public class Admin extends User {

    public Admin(String username){
        super(username);
        setTester(true);
        setCreator(true);
    }

}
