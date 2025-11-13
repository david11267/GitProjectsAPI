package david.git_projects_api.dtos;

import david.git_projects_api.domain.ApiKey;

import java.util.ArrayList;

public record ProjectsRequest(ApiKey apiKey, ArrayList<String> repos) {
}
