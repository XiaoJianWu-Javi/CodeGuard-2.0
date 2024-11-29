package es.tfg.codeguard.util;

public class UsernameAlreadyExistException extends RuntimeException {
    public UsernameAlreadyExistException(String message) {
        super(message);
    }
}
