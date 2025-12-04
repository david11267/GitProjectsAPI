package david.git_projects_api.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import david.git_projects_api.domain.TimeStamps.ApiTimestamp;
import david.git_projects_api.dtos.RepoSummaryCollection;
import david.git_projects_api.exceptions.ApiException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.http.HttpStatus;

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

    @OneToOne(mappedBy = "apikey")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "apiKey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApiTimestamp> timestamps = new ArrayList<>();

    private String aiModel;

    @ElementCollection
    @CollectionTable(name = "api_key_blacklist", joinColumns = @JoinColumn(name = "api_key_id"))
    @Column(name = "value")
    private List<String> blacklist = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "cached_results", columnDefinition = "jsonb") // Use "json" for MySQL, "jsonb" for Postgres
    private RepoSummaryCollection cachedResults;

    public ApiKey() {
        this.id = UUID.randomUUID();
        this.key = UUID.randomUUID();
        this.quota = 10;
        this.timestamps.add(new ApiTimestamp("Initialized api key",this));
        this.blacklist= new ArrayList<>();
        this.aiModel = "gemini-2.5-flash";
    }

    public void consumeQuota(){
        this.setQuota(quota-1);
    }

    public void validateQuota(){
        if (this.quota <= 0) throw new ApiException("you are out of requests. Either buy increased quota or wait until tomorrow", HttpStatus.PAYMENT_REQUIRED);
    }
}
