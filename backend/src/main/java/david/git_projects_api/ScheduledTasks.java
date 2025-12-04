package david.git_projects_api;

import david.git_projects_api.domain.ApiKey;
import david.git_projects_api.repositories.ApiKeyRepository;
import david.git_projects_api.services.ApiKeyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;

@Component
public class ScheduledTasks {
    private final ApiKeyRepository apiKeyRepository;
    private final ApiKeyService apiKeyService;
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    public ScheduledTasks(ApiKeyRepository apiKeyRepository, ApiKeyService apiKeyService) {
        this.apiKeyRepository = apiKeyRepository;
        this.apiKeyService = apiKeyService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void resetUserRequestBudgets() {
        log.info("Resetting RequestBudget for all users");
        apiKeyRepository.resetAllUserRequestBudgets();
    }

    @Scheduled(cron = "0 0 2 * * *")
    public void cacheAllUserProjects() throws IOException, InterruptedException {
        log.info("Caching batch started_________ ");
        List<ApiKey> apiKeys= apiKeyRepository.findAll();

        HashMap<UUID,Boolean> map = new HashMap<>();
        for (ApiKey key : apiKeys) {
            map.put(key.getKey(), false);
        }

        for (int i = 0; i < 2; i++) {
            // run 3 times
            log.info("Caching round_________ "+i);

            for (Map.Entry<UUID, Boolean> entry : map.entrySet()) {

                if (!entry.getValue()) {
                    UUID id = entry.getKey();
                    log.info("Handling id: "+id);
                    apiKeyService.handleProjectsRequest(id,true);
                    entry.setValue(true);
                }
            }
        }
        log.info("Caching batch over_________");

    }
}
