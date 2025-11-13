package david.git_projects_api.services;
import com.fasterxml.jackson.databind.node.ObjectNode;
import david.git_projects_api.domain.ApiKey;
import david.git_projects_api.domain.TimeStamps.ApiTimestamp;
import david.git_projects_api.domain.User;
import david.git_projects_api.dtos.ProjectsRequest;
import david.git_projects_api.dtos.RepoSummaryDtoCollection;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class ProjectsService {
    private final GithubApiService githubApiService;
    private  final UserService userService;
    private final GeminiAiService geminiAiService;
    private final TimestampService timestampService;

    public ProjectsService(UserService userService,GithubApiService githubApiService, GeminiAiService geminiAiService, TimestampService timestampService) {
        this.userService = userService;
        this.githubApiService= githubApiService;
        this.geminiAiService = geminiAiService;
        this.timestampService= timestampService;
    }

    public RepoSummaryDtoCollection handleProjectsRequest(ProjectsRequest request) throws IOException, InterruptedException {
        System.out.println("Received request from API key: " + request.apiKey());
        System.out.println("Repos: " + request.repos());

        User user =userService.getOrCreateUser(request.apiKey());
        ArrayList<ObjectNode> rawJson =  githubApiService.handleGithubFetches(request, user.getUsername());

        RepoSummaryDtoCollection repoAnalysisDtoList =  geminiAiService.generateContent(rawJson);
        handleSuccessfulRequest(user.getApikey());
        return repoAnalysisDtoList;
    }

    private void handleSuccessfulRequest(ApiKey apikey){
        timestampService.createTimestamp(new ApiTimestamp("API handled projects request",apikey));


    }
}


