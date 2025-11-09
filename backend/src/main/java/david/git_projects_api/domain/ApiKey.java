package david.git_projects_api.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;
@Getter
@Setter
@Entity
@Table(name = "api_keys")

public class ApiKey {
    @Id
    private UUID id;
    private UUID key;
    private int quota; //remaining api cals
    private Instant issuedAt;
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public ApiKey() {
        this.id = UUID.randomUUID();
        this.issuedAt = Instant.now();
        this.key = UUID.randomUUID();
        this.quota = 10;
    }
}
