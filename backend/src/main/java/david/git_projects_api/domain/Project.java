package david.git_projects_api.domain;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class Project
{
    private Long id;
    private String name;
    private String fullName;
    private GitHubUser owner;
    private String htmlUrl;
    private String description;
    private List<String> topics;
    private String language;  // primary
    private Map<String, Long> languages;  // breakdown
    private Integer stargazersCount;
    private Integer forksCount;
    private Integer openIssuesCount;
    private Integer watchersCount;
    private String defaultBranch;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant pushedAt;

    // Custom / derived fields
    private List<String> dependencies;
    private String framework;
    private String architecture;
    private String readmeSummary;
    private Long sizeInKB;

}
