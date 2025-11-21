package david.git_projects_api.services;

import com.fasterxml.jackson.databind.node.ObjectNode;
import david.git_projects_api.domain.ApiKey;
import david.git_projects_api.domain.TimeStamps.ApiTimestamp;
import david.git_projects_api.dtos.OptionsDto;
import david.git_projects_api.dtos.ProjectsRequest;
import david.git_projects_api.dtos.RepoSummaryDtoCollection;
import david.git_projects_api.exceptions.ApiException;
import david.git_projects_api.repositories.ApiKeyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    public void updateKey(UUID key, OptionsDto options){
        ApiKey apiKey = validateAndGetApiKey(key);
        apiKey.setAiModel(options.aiModel());
        apiKey.setBlacklist(options.blacklist());

        List<ApiTimestamp> timestamps = apiKey.getTimestamps();
        timestamps.add(new ApiTimestamp("Updated key", apiKey));
        apiKey.setTimestamps(timestamps);

        apiKeyRepository.save(apiKey);
    }
    public RepoSummaryDtoCollection handleProjectsRequest(ApiKey apiKey) throws IOException, InterruptedException {

        ArrayList<ObjectNode> githubJson =  githubApiService.handleGithubFetches(apiKey);

        //Ai service____________
        RepoSummaryDtoCollection aiCompletedCollection =  geminiAiService.generateContent(githubJson,apiKey.getAiModel());
        handleSuccessfulRequest(apiKey);
        return aiCompletedCollection;
    }


    private void handleSuccessfulRequest(ApiKey apikey){
        timestampService.createTimestamp(new ApiTimestamp("API handled projects request",apikey));
        apikey.consumeQuota();
        apiKeyRepository.save(apikey);
    }
}
