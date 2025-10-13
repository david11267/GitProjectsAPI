package david.git_projects_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin
public class GitProfileController {
    @PostMapping
    public ResponseEntity<?> getProjects(@RequestBody ArrayList<String> repos) {
        return ResponseEntity.ok().body(repos);
    }


    @GetMapping("/health")
    public ResponseEntity<?> health() {
        return ResponseEntity.ok().build();
    }

}
