package es.tfg.codeguard.model.entity.userpass;

import es.tfg.codeguard.util.UsernameNotValidException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.NoSuchElementException;

@Entity
@Table(name = "USERPASS")
public class UserPass {

    private static final String USERNAME_REGEXP = "^[a-zA-Z]{3,}\\w*$";

    @Id
    @NotBlank
    @Pattern(regexp = USERNAME_REGEXP, message = "{username.invalidPattern}")
    private String username;

    @NotBlank
    private String hashedPass;

    private Boolean admin;

    public UserPass() {
        setAdmin(false);
    }

    public UserPass(String username, String hashedPass, boolean admin) {
        this();
        setUsername(username);
        setHashedPass(hashedPass);
        setAdmin(admin);
    }

    public String getUsername() {
        if (username == null) throw new NoSuchElementException();
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.isBlank()) throw new UsernameNotValidException("Username not valid [" +username +"]");
        if (!username.matches(USERNAME_REGEXP)) throw new UsernameNotValidException("Username not valid [" +username +"]");
        this.username = username;
    }

    public String getHashedPass() {
        if (hashedPass == null) throw new NoSuchElementException();
        return hashedPass;
    }

    public void setHashedPass(String hashedPass) {
        if (hashedPass == null || hashedPass.isBlank()) throw new IllegalArgumentException();
        this.hashedPass = hashedPass;
    }

    public Boolean isAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

}