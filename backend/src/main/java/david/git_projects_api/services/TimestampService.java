package david.git_projects_api.services;

import david.git_projects_api.domain.ApiKey;
import david.git_projects_api.domain.TimeStamps.ApiTimestamp;
import david.git_projects_api.domain.TimeStamps.Timestamp;
import david.git_projects_api.repositories.ApiKeyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public  class TimestampService {
    private final ApiKeyRepository apiKeyRepository;


    public TimestampService(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    public  void createTimestamp(Timestamp timestamp) {
        switch (timestamp) {
            case ApiTimestamp apiTs -> handleApiTimestamp(apiTs);
            default -> throw new IllegalArgumentException(
                    "Unknown timestamp type: " + timestamp.getClass().getSimpleName()
            );
        }


    }

    private void handleApiTimestamp(ApiTimestamp apiTs) {
        ApiKey apiKey = apiTs.getApiKey();
        apiKey.getTimestamps().add(apiTs);
        this.apiKeyRepository.save(apiKey);
    }
}