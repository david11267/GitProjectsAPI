package david.git_projects_api.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import david.git_projects_api.domain.TimeStamps.ApiTimestamp;
import david.git_projects_api.domain.TimeStamps.Timestamp;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "api_keys")

public class ApiKey {
    @Id
    private UUID id;
    private UUID key;
    private int quota; //remaining api cals
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "apiKey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApiTimestamp> timestamps = new ArrayList<>();

    //Api key options
    boolean enableAi;

    public ApiKey() {
        this.id = UUID.randomUUID();
        this.key = UUID.randomUUID();
        this.quota = 10;
        this.enableAi = true;
        this.timestamps.add(new ApiTimestamp("Initialized api key",Instant.now(), this));
    }
}
