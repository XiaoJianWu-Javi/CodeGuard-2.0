package es.tfg.codeguard.model.dto;

import es.tfg.codeguard.model.entity.UserPass;

public class UserPassDTO {
	
    private String username;
    private boolean admin;

    public UserPassDTO() {
    }
    
    public UserPassDTO(UserPass userPass) {
    	setUsername(userPass.getUsername());
    	setAdmin(userPass.isAdmin());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
