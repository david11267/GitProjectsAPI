package david.git_projects_api.controllers;

import david.git_projects_api.domain.User;
import david.git_projects_api.dtos.UserDto;
import david.git_projects_api.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class GitProfileController {
    UserService userService;

    public GitProfileController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/projects")
    public ResponseEntity<?> getProjects(@RequestBody ArrayList<String> repos) {
        return ResponseEntity.ok().body(repos);
    }

    /**
     * Checks if the user is registered in db.
     * If not registers user and creates an api key.
     *
     * @param jwt the JWT of the authenticated user
     * @return Api key and usageDetails
     */
    @GetMapping("/key")
    public String key(@AuthenticationPrincipal Jwt jwt) {
        UserDto dto = UserDto.jwtToDto(jwt);
        //User user = userService.getOrCreateUser(dto);
        return "Hello " + jwt.getClaim("email");
    }

    @GetMapping("/health")
    public ResponseEntity<?> health() {
        return ResponseEntity.ok("health check is good");
    }
}
