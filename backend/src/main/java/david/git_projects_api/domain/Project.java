package david.git_projects_api.domain;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
@Entity
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
    private List<String> dependencies;
    private String architecture;
    private String readmeSummary;
    @Column(name = "size_in_kb")
    private int sizeInKB;

    @ManyToMany
    @JoinTable(
            name = "project_skills",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skill> skills = new ArrayList<>();



}
