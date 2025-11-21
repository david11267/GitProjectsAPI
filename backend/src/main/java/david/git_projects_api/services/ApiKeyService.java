package david.git_projects_api.services;

import com.fasterxml.jackson.databind.node.ObjectNode;
import david.git_projects_api.domain.ApiKey;
import david.git_projects_api.domain.TimeStamps.ApiTimestamp;
import david.git_projects_api.dtos.ProjectsRequest;
import david.git_projects_api.dtos.RepoSummaryDtoCollection;
import david.git_projects_api.exceptions.ApiException;
import david.git_projects_api.repositories.ApiKeyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class ApiKeyService {
    private final GithubApiService githubApiService;
    private final GeminiAiService geminiAiService;
    private final TimestampService timestampService;
    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyService(GithubApiService githubApiService, GeminiAiService geminiAiService, TimestampService timestampService, ApiKeyRepository apiKeyRepository) {
        this.githubApiService= githubApiService;
        this.geminiAiService = geminiAiService;
        this.timestampService= timestampService;
        this.apiKeyRepository = apiKeyRepository;
    }

    public ApiKey validateAndGetApiKey(UUID apiKey){
        ApiKey key = apiKeyRepository.findDistinctByKey(apiKey);
        key.validateQuota();
        if (key == null) throw new ApiException("API key: "+apiKey+" was not found", HttpStatus.NOT_FOUND);
        return key;
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
