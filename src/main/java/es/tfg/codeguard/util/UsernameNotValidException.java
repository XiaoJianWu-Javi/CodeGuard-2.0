package es.tfg.codeguard.util;

public class UsernameNotValidException extends RuntimeException {
    public UsernameNotValidException(String message) {
        super(message);
    }
}
