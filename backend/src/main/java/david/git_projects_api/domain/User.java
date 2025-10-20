package david.git_projects_api.domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    private String id;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String profileImage;
    Instant experation;
    Instant issuedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects;

    public User(String id, String profileImage, String username, String name, String surname, String email, Instant experation, Instant issuedAt) {
        this.id = id;
        this.profileImage = profileImage;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.experation =experation;
        this.issuedAt = issuedAt;
    }



}
