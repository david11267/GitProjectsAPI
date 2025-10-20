package david.git_projects_api.domain;

import david.git_projects_api.domain.enums.SkillType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;
    private String icon;

    @Enumerated(EnumType.STRING)
    private SkillType type;

    @ManyToMany(mappedBy = "skills")
    private Set<User> users = new HashSet<>();
}
