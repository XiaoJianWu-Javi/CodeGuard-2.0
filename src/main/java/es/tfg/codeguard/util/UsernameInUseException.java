package es.tfg.codeguard.util;

public class UsernameInUseException extends RuntimeException {
    public UsernameInUseException(String message) {
        super(message);
    }
}
