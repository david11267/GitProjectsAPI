package david.git_projects_api.domain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


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
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private ApiKey apikey;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects;


    public User(String id, String profileImage, String username, String name, String surname, String email) {
        this.id = id;
        this.profileImage = profileImage;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.apikey= new ApiKey();
        this.apikey.setUser(this);
    }
}
