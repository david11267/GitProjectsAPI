package david.git_projects_api.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GitHubUser {
    private String login;
    private Long id;
    private String avatarUrl;
    private String htmlUrl;

    public GitHubUser(String login, Long id, String avatarUrl, String htmlUrl) {
        this.login = login;
        this.id = id;
        this.avatarUrl = avatarUrl;
        this.htmlUrl = htmlUrl;
    }

}
