package david.git_projects_api.dtos;

import lombok.Data;

import java.util.List;
@Data
public class RepoSummaryDto {
        private String name;
        private String html_url;
        private String description;
        private List<Technology> languages;
        private List<Technology> frameworks;
        private List<Technology> tools;
        private String architecture;
        private String deployment;
}
@Data
class Technology{
        private  String name;
        private  String description;
        private  String usage;
        private  String iconUrl;
}
