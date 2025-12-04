package david.git_projects_api.controllers;

import david.git_projects_api.domain.ApiKey;
import david.git_projects_api.domain.User;
import david.git_projects_api.dtos.OptionsDto;
import david.git_projects_api.dtos.RepoSummaryCollection;
import david.git_projects_api.dtos.UserDto;
import david.git_projects_api.services.ApiKeyService;
import david.git_projects_api.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api")
public class GitProfileController {
    UserService userService;
    ApiKeyService apiKeyService;
    public GitProfileController(UserService userService, ApiKeyService apiKeyService) {
        this.userService = userService;
        this.apiKeyService= apiKeyService;
    }

    @GetMapping("/projects")
    public ResponseEntity<?> getProjects(
            @RequestParam("apiKey") String key) throws IOException, InterruptedException {
        RepoSummaryCollection result = apiKeyService.handleProjectsRequest(UUID.fromString(key));
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

    @PutMapping("/options")
    public ResponseEntity<?> updateKeyOptions(@AuthenticationPrincipal Jwt jwt, @RequestBody OptionsDto options) {
        apiKeyService.updateKey(UUID.fromString(options.key()),options);
        return ResponseEntity.ok(options);
    }

    @GetMapping("/health")
    public ResponseEntity<?> health() {
        return ResponseEntity.ok("health check is good");
    }
}
