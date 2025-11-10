package david.git_projects_api.domain;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Entity
@Setter
public class ApiKeySettings {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Boolean enableAi;
    @OneToOne(mappedBy = "settings")
    @JsonBackReference
    private ApiKey apiKey;

    public ApiKeySettings() {
        this.enableAi=true;
    }
}
