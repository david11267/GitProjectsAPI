package david.git_projects_api.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class Project
{
    @Id
    private String id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String htmlUrl;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant pushedAt;
    private List<String> topics;

 /*   private Integer stargazersCount;
    private Integer forksCount;
    private Integer openIssuesCount;
    private Integer watchersCount;
    private String defaultBranch;*/


    // Custom / derived fields
    private List<String> dependencies;
    private List<Skill> skills; //contains languages, frameworks, tools, other
    private String architecture;
    private String readmeSummary;
    private double sizeInKB;



}
