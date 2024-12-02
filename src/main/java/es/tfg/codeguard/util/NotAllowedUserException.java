package es.tfg.codeguard.util;

public class NotAllowedUserException extends RuntimeException {
    public NotAllowedUserException(String message) {
        super(message);
    }
}
