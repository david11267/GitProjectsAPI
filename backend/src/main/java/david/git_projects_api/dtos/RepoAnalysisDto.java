package david.git_projects_api.dtos;

import java.util.List;

public class RepoAnalysisDto {
    public Repository repository;
    public List<Language> languages;
    public Frameworks frameworks;
    public List<Tool> tools_and_integrations;
    public Architecture architecture;
    public List<ExternalService> external_services;
    public BuildAndDeployment build_and_deployment;
    public DeveloperExperience developer_experience;

    public static class Repository {
        public String name;
        public String description;
        public String overview;
    }

    public static class Language {
        public String name;
        public long bytes;
        public String role;
    }

    public static class Frameworks {
        public List<String> backend;
        public List<String> frontend;
        public List<String> testing;
        public List<String> build;
    }

    public static class Tool {
        public String name;
        public String category;
        public String description;
    }

    public static class Architecture {
        public String type;
        public List<Module> modules;
        public List<String> design_patterns;
    }

    public static class Module {
        public String name;
        public String role;
        public List<String> key_paths;
    }

    public static class ExternalService {
        public String name;
        public String purpose;
    }

    public static class BuildAndDeployment {
        public List<String> build_tools;
        public List<String> containerization;
        public List<String> ci_cd;
    }

    public static class DeveloperExperience {
        public String code_organization;
        public String maintainability;
        public List<String> missing_files;
    }
}
