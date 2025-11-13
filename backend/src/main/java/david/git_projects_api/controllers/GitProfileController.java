package david.git_projects_api.controllers;

import david.git_projects_api.domain.ApiKey;
import david.git_projects_api.domain.User;
import david.git_projects_api.dtos.ProjectsRequest;
import david.git_projects_api.dtos.RepoSummaryDtoCollection;
import david.git_projects_api.dtos.UserDto;
import david.git_projects_api.services.ApiKeyService;
import david.git_projects_api.services.ProjectsService;
import david.git_projects_api.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class GitProfileController {
    UserService userService;
    ProjectsService projectsService;
    ApiKeyService apiKeyService;
    public GitProfileController(UserService userService, ProjectsService projectsService,ApiKeyService apiKeyService) {
        this.userService = userService;
        this.projectsService = projectsService;
        this.apiKeyService= apiKeyService;
    }

    @PostMapping("/projects")
    public ResponseEntity<?> getProjects(
            @RequestBody ArrayList<String> repos,
            @RequestHeader("apiKey") String key) throws IOException, InterruptedException {
        ApiKey apiKey = apiKeyService.validateAndGetApiKey(UUID.fromString(key));
        RepoSummaryDtoCollection result =projectsService.handleProjectsRequest(new ProjectsRequest(apiKey,repos));
        return ResponseEntity.ok().body(result);

    }

    /**
     * Checks if the user is registered in db.
     * If not, registers the user and creates an api key.
     *
     * @param jwt the JWT of the authenticated user
     * @return Api key and usageDetails
     */
    @GetMapping("/key")
    public ResponseEntity<ApiKey> key(@AuthenticationPrincipal Jwt jwt) {
        UserDto dto = UserDto.jwtToDto(jwt);
        User user = userService.getOrCreateUser(dto);
        return ResponseEntity.ok(user.getApikey());
    }

    @GetMapping("/health")
    public ResponseEntity<?> health() {
        return ResponseEntity.ok("health check is good");
    }
}
