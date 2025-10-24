package david.git_projects_api.exceptions;

import org.springframework.http.HttpStatus;

public abstract class ApiException extends RuntimeException {
    private final HttpStatus status;
    private final String type;

    protected ApiException(String type, String message, HttpStatus status) {
        super(message);
        this.type = type;
        this.status = status;
    }

    public String getType() { return type; }
    public HttpStatus getStatus() { return status; }
}
