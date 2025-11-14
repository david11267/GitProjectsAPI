package david.git_projects_api.dtos;

import lombok.Data;

import java.util.List;

public class RepoSummaryDto {
        public String name;
        public String description;
        public List<Technology> languages;
        public List<Technology> frameworks;
        public List<Technology> tools;
        public String architecture;
        public String deployment;
}
@Data
class Technology{
        private  String name;
        private  String description;
        private  String usage;
        private  String iconUrl;
}
