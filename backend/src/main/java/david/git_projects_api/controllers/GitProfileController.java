package david.git_projects_api.controllers;

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

    @GetMapping("/health")
    public ResponseEntity<?> health() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public String me(@AuthenticationPrincipal Jwt jwt) {
        return "Hello " + jwt.getClaim("email");
    }
}
