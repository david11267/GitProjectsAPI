package david.git_projects_api.domain.TimeStamps;

import com.fasterxml.jackson.annotation.JsonBackReference;
import david.git_projects_api.domain.ApiKey;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "api_timestamps")
public class ApiTimestamp extends Timestamp {

    @ManyToOne
    @JoinColumn(name = "api_key_id")
    @JsonBackReference
    private ApiKey apiKey;

    public ApiTimestamp(String action, Instant timestamp, ApiKey apiKey) {
        super(action, timestamp); // must call the base constructor
        this.apiKey = apiKey;
    }
}
