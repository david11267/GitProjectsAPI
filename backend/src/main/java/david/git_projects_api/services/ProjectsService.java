package david.git_projects_api.services;
import com.fasterxml.jackson.databind.node.ObjectNode;
import david.git_projects_api.domain.ApiKey;
import david.git_projects_api.domain.TimeStamps.ApiTimestamp;
import david.git_projects_api.dtos.ProjectsRequest;
import david.git_projects_api.dtos.RepoSummaryDtoCollection;
import david.git_projects_api.repositories.ApiKeyRepository;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class ProjectsService {
    private final GithubApiService githubApiService;
    private final GeminiAiService geminiAiService;
    private final TimestampService timestampService;
    private final ApiKeyRepository apiKeyRepository;


    public ProjectsService(GithubApiService githubApiService, GeminiAiService geminiAiService, TimestampService timestampService, ApiKeyRepository apiKeyRepository) {
        this.githubApiService= githubApiService;
        this.geminiAiService = geminiAiService;
        this.timestampService= timestampService;
        this.apiKeyRepository = apiKeyRepository;
    }

    public RepoSummaryDtoCollection handleProjectsRequest(ProjectsRequest request) throws IOException, InterruptedException {
        System.out.printf("""
                Received request from API key: %s
                Repos: %s
                """, request.apiKey().getKey(),request.repos());
        ArrayList<ObjectNode> githubJson =  githubApiService.handleGithubFetches(request);
        RepoSummaryDtoCollection manuallyFilledCollection =  manuallyAnalyzeGithubJson(githubJson);

        //Ai service____________
        RepoSummaryDtoCollection aiCompletedCollection =  geminiAiService.generateContent(githubJson);
        handleSuccessfulRequest(request.apiKey());
        return aiCompletedCollection;
    }

    private RepoSummaryDtoCollection manuallyAnalyzeGithubJson(ArrayList<ObjectNode> githubJson) {
        for (ObjectNode json:githubJson){

        }
        return null;
    }

    private void handleSuccessfulRequest(ApiKey apikey){
        timestampService.createTimestamp(new ApiTimestamp("API handled projects request",apikey));
        apikey.consumeQuota();
        apiKeyRepository.save(apikey);
    }
}


