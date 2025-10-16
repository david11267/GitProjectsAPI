package david.git_projects_api.controllers;

import david.git_projects_api.dtos.ClerkUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class GitProfileController {

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
        ClerkUserDto dto = ClerkUserDto.jwtToDto(jwt);
        return "Hello " + jwt.getClaim("email");
    }

    @GetMapping("/health")
    public ResponseEntity<?> health() {
        return ResponseEntity.ok().build();
    }
}
