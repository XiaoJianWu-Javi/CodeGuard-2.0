package es.tfg.codeguard.model.dto;

public class ApiResponse<T> {

    private int status;
    private String message;
    private AuthToken authToken;

    public ApiResponse(int status, String message, AuthToken authToken) {
        this.status = status;
        this.message = message;
        this.authToken = authToken;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
