package david.git_projects_api.domain.TimeStamps;

import david.git_projects_api.repositories.ApiKeyRepository;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class Timestamp {
    @Id
    @GeneratedValue
    private Long id;
    protected String action;
    protected Instant timestamp;

    public Timestamp(String timestampText) {
        this.action = timestampText;
        this.timestamp = Instant.now() ;
    }
}
