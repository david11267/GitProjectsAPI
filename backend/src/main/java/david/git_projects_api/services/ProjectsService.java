package david.git_projects_api.services;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import david.git_projects_api.domain.User;
import david.git_projects_api.dtos.ProjectsRequest;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Service
public class ProjectsService {
    private final GithubApiService githubApiService;
    private  final UserService userService;
    public ProjectsService(UserService userService,GithubApiService githubApiService) {
        this.userService = userService;
        this.githubApiService= githubApiService;
    }


    public ArrayList<ObjectNode>  handleProjectsRequest(ProjectsRequest request) throws IOException, InterruptedException {
        System.out.println("Received request from API key: " + request.apiKey());
        System.out.println("Repos: " + request.repos());
        User user =userService.getOrCreateUser(request.apiKey());
        ArrayList<ObjectNode> result =  githubApiService.handleGithubFetches(request, user.getUsername());
        return result;
    }
}


