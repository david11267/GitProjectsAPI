package david.git_projects_api.exceptions;

public class InvalidApiKeyException extends RuntimeException {
    public InvalidApiKeyException(String apiKey) {
        super("API key is invalid or unauthorized: " + apiKey);
    }
}