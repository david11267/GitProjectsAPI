package david.git_projects_api.services;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.genai.types.GenerateContentResponse;
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
    private final GeminiAiService geminiAiService;
    public ProjectsService(UserService userService,GithubApiService githubApiService, GeminiAiService geminiAiService) {
        this.userService = userService;
        this.githubApiService= githubApiService;
        this.geminiAiService = geminiAiService;
    }


    public ArrayList<ObjectNode>  handleProjectsRequest(ProjectsRequest request) throws IOException, InterruptedException {
        System.out.println("Received request from API key: " + request.apiKey());
        System.out.println("Repos: " + request.repos());
        User user =userService.getOrCreateUser(request.apiKey());
        ArrayList<ObjectNode> rawJson =  githubApiService.handleGithubFetches(request, user.getUsername());
        GenerateContentResponse aiPolishedJson =  geminiAiService.generateContent(rawJson);
        String aiAnswer = aiPolishedJson.text();
        return rawJson;
    }
}


