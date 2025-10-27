package david.git_projects_api.dtos;

import java.util.ArrayList;

public record GithubMetaData(String description,
                             String url,
                             String collaborators_url,
                             String languages_url,
                             String git_commits_url,
                             String trees_url,
                             String mainBranchSha) { }
