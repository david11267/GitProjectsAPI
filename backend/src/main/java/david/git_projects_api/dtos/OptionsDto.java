package david.git_projects_api.dtos;

import java.util.List;
public record OptionsDto(
        String key,
        // @NotBlank(message = "AI Model is required")
        String aiModel,
        // @NotNull
        List<String> blacklist,
        // @NotNull
        List<String> whitelist
) { }