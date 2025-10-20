package david.git_projects_api.domain;
import david.git_projects_api.domain.enums.SkillType;
import jakarta.persistence.*;
@Entity
@Table(name = "skills")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private SkillType type;
}
