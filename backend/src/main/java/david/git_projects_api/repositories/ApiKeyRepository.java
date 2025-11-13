package david.git_projects_api.repositories;

import david.git_projects_api.domain.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ApiKeyRepository extends JpaRepository<ApiKey, UUID> {
    List<ApiKey> findApiKeyById(UUID id);

    ApiKey findDistinctById(UUID id);

    ApiKey findDistinctByKey(UUID key);
}
