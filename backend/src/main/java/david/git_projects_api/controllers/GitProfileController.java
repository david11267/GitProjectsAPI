package david.git_projects_api.controllers;

import david.git_projects_api.domain.User;
import david.git_projects_api.dtos.UserDto;
import david.git_projects_api.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class GitProfileController {
    UserService userService;

    public GitProfileController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/projects")
    public ResponseEntity<?> getProjects(
            @RequestBody ArrayList<String> repos,
            @RequestHeader("apiKey") String apiKey) {
        System.out.println("Received request from API key: " + apiKey);
        System.out.println("Repos: " + repos);
        if (userService.validateUserApiKey(apiKey)){
            return ResponseEntity.ok().body(repos);
        }
        return ResponseEntity.badRequest().body("key is likley invalid");
    }

    /**
     * Checks if the user is registered in db.
     * If not registers user and creates an api key.
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
