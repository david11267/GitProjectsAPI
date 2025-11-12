package david.git_projects_api.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class RepoSummaryDtoCollection {
    public List<RepoSummaryDtoCollection> repoSummaryDtoCollections;
}

    class RepoSummaryDto {
        public String name;
        public String description;
        public List<String> languages;
        public List<String> frameworks;
        public List<String> tools;
        public String architecture;
        public String deployment;
    }
