package david.git_projects_api.repositories;

import david.git_projects_api.domain.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

public interface ApiKeyRepository extends JpaRepository<ApiKey, UUID> {
    ApiKey findDistinctByKey(UUID key);

    @Modifying
    @Transactional
    @Query("UPDATE ApiKey a SET a.quota = 11")
    void resetAllUserRequestBudgets();
}
