package es.tfg.codeguard.model.dto;

import es.tfg.codeguard.model.entity.userpass.UserPass;

public record UserPassDTO(String username, boolean admin) {

    public UserPassDTO(UserPass userPass) {
        this(userPass.getUsername(), userPass.isAdmin());
    }
}
