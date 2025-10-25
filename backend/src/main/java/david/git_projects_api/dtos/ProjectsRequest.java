package david.git_projects_api.dtos;

import java.util.ArrayList;
import java.util.UUID;

public record ProjectsRequest(UUID apiKey, ArrayList<String> repos) {
}
