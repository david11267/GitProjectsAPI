package david.git_projects_api.repositories;

import david.git_projects_api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepositorty extends JpaRepository<User,String> {
    Optional<User> findById(String id);

    User getUserById(String id);

    boolean existsByApikey_Key(UUID apikeyKey);

    User getUsersByApikey_Key(UUID apikeyKey);
}
