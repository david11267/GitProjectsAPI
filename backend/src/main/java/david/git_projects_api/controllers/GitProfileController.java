package david.git_projects_api.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import david.git_projects_api.domain.User;
import david.git_projects_api.dtos.ProjectsRequest;
import david.git_projects_api.dtos.UserDto;
import david.git_projects_api.exceptions.InvalidApiKeyException;
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

    public GitProfileController(UserService userService, ProjectsService projectsService) {
        this.userService = userService;
        this.projectsService = projectsService;
    }

    @PostMapping("/projects")
    public ResponseEntity<?> getProjects(
            @RequestBody ArrayList<String> repos,
            @RequestHeader("apiKey") String apiKey) throws IOException, InterruptedException {
        if (!userService.validateUserApiKey(apiKey)) throw new InvalidApiKeyException(apiKey);

        ProjectsRequest request = new ProjectsRequest(UUID.fromString(apiKey),repos);
        ArrayList<ObjectNode> result =projectsService.handleProjectsRequest(request);
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
    public ResponseEntity<UUID> key(@AuthenticationPrincipal Jwt jwt) {
        UserDto dto = UserDto.jwtToDto(jwt);
        User user = userService.getOrCreateUser(dto);
        return ResponseEntity.ok(user.getApiKey());
    }

    @GetMapping("/health")
    public ResponseEntity<?> health() {
        return ResponseEntity.ok("health check is good");
    }
}
