package david.git_projects_api.services;

import david.git_projects_api.domain.ApiKey;
import david.git_projects_api.exceptions.ApiException;
import david.git_projects_api.repositories.ApiKeyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApiKeyService {
    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyService(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    public ApiKey validateAndGetApiKey(UUID apiKey){
        ApiKey key = apiKeyRepository.findDistinctByKey(apiKey);
        key.validateQuota();
        if (key == null) throw new ApiException("API key: "+apiKey+" was not found", HttpStatus.NOT_FOUND);
        return key;
    }
}
