package david.git_projects_api.dtos;

import com.google.genai.types.Schema;
import java.util.List;
import java.util.Map;

    public class RepoSummaryDtoCollection {
        public List<RepoSummaryDto> repoSummaryDtoCollections;

        public static Schema getTechnologySchema() {
            return Schema.builder()
                    .type("object")
                    .properties(Map.of(
                            "name", Schema.builder().type("string").description("Technology name").build(),
                            "description", Schema.builder().type("string").description("Short description").build(),
                            "usage", Schema.builder().type("string").description("How the technology is used").build(),
                            "iconUrl", Schema.builder().type("string").description("Optional icon URL").build()
                    ))
                    .build();
        }

        public static Schema getRepoSummarySchema() {
            return Schema.builder()
                    .type("object")
                    .properties(Map.of(
                            "name", Schema.builder().type("string").description("Repository name").build(),
                            "html_url", Schema.builder().type("string").description("Public repository url").build(),
                            "description", Schema.builder().type("string").description("Repository description").build(),

                            "languages", Schema.builder()
                                    .type("array")
                                    .items(getTechnologySchema())
                                    .description("List of programming languages used")
                                    .build(),

                            "frameworks", Schema.builder()
                                    .type("array")
                                    .items(getTechnologySchema())
                                    .description("List of frameworks used")
                                    .build(),

                            "tools", Schema.builder()
                                    .type("array")
                                    .items(getTechnologySchema())
                                    .description("Developer tools and utilities")
                                    .build(),

                            "architecture", Schema.builder()
                                    .type("string")
                                    .description("Architecture style or pattern")
                                    .build(),

                            "deployment", Schema.builder()
                                    .type("string")
                                    .description("Deployment environment or method")
                                    .build()
                    ))
                    .build();
        }

        public static Schema getRepoSummaryCollectionSchema() {
            return Schema.builder()
                    .type("object")
                    .properties(Map.of(
                            "repoSummaryDtoCollections",
                            Schema.builder()
                                    .type("array")
                                    .items(getRepoSummarySchema())
                                    .build()
                    ))
                    .build();
        }
    }

