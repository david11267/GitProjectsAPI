package david.git_projects_api;

import david.git_projects_api.repositories.ApiKeyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    private final ApiKeyRepository apiKeyRepository;
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    public ScheduledTasks(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void resetUserRequestBudgets() {
        log.info("Resetting RequestBudget for all users");
        apiKeyRepository.resetAllUserRequestBudgets();
    }

    @Scheduled(cron = "0 0 2 * * *")
    public void cacheAllUserProjects() {
        log.info("Caching all user projects");

    }
}
