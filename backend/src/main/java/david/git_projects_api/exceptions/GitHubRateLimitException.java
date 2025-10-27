package david.git_projects_api.exceptions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter

public class GitHubRateLimitException extends RuntimeException {
    private final ObjectNode errorBody;

    public GitHubRateLimitException(ObjectNode errorBody) {
        super("The API has exceeded GitHub rate limit");
        this.errorBody = errorBody;
    }

}