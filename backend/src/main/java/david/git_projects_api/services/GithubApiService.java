package david.git_projects_api.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import david.git_projects_api.dtos.ProjectsRequest;
import david.git_projects_api.exceptions.GitHubRateLimitException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GithubApiService {

    public ArrayList<Map<String, JsonNode>> handleGithubFetches(ProjectsRequest request, String githubUsername) throws IOException, InterruptedException {
       ArrayList<Map<String,JsonNode>> fullDataList = new ArrayList<>();

        for(String repoName:request.repos()){
        String repoUrl = buildGithubUrl(repoName, githubUsername);
        JsonNode metaData =fetchMetaData(repoUrl);
        Map<String, JsonNode> fullData = fullDataFetch(metaData);
        fullDataList.add(fullData);
       }

        return fullDataList;
    }

    public static String buildGithubUrl(String repoName, String githubUsername) {
        String baseApiUrl = "https://api.github.com/repos/" + githubUsername;
        return baseApiUrl + "/" + repoName;
    }

    public static JsonNode fetchMetaData(String url) throws IOException, InterruptedException {
        var client = HttpClient.newHttpClient();
        var mapper = new ObjectMapper();

        // 1️⃣ Fetch repo metadata
        var metaRequest = HttpRequest.newBuilder(URI.create(url))
                .header("Accept", "application/vnd.github.v3+json")
                .build();
        var metaResponse = client.send(metaRequest, HttpResponse.BodyHandlers.ofString());
        if (metaResponse.statusCode() != 200) {
            ObjectNode errorNode = mapper.createObjectNode();
            errorNode.put("status",metaResponse.statusCode());
            errorNode.put("message", "GitHub API error");
            errorNode.put("body", metaResponse.body());
            throw new GitHubRateLimitException(errorNode); // custom exception
        }

        ObjectNode metaJson = (ObjectNode) mapper.readTree(metaResponse.body());

        // 2️⃣ Fetch all branches
        String branchesUrl = metaJson.get("branches_url").asText().replace("{/branch}", "/");
        var branchRequest = HttpRequest.newBuilder(URI.create(branchesUrl)).GET().build();
        var branchResponse = client.send(branchRequest, HttpResponse.BodyHandlers.ofString());
        JsonNode branchJson = mapper.readTree(branchResponse.body());

        // 3️⃣ Get last branch name
        String lastBranchName = branchJson.get(branchJson.size() - 1).get("name").asText();

        // Replace all {/sha} URLs dynamically
        metaJson.fieldNames().forEachRemaining(field -> {
            String value = metaJson.get(field).asText();
            if (value.contains("{/sha}")) {
                metaJson.put(field, value.replace("{/sha}", "/"+lastBranchName));
            }
        });

        return metaJson;
    }

    public static Map<String, JsonNode> fullDataFetch(JsonNode metaData) {
        var client = HttpClient.newHttpClient();
        var mapper = new ObjectMapper();

        // List of fields we want to fetch
        String[] preCheckedFields = {"html_url","description","git_commits_url", "trees_url", "commits_url","languages_url","homepage",""};

        // Build key -> URL map for the fields that exist
        Map<String, String> urls = Arrays.stream(preCheckedFields)
                .filter(metaData::has) // only include if present
                .collect(Collectors.toMap(
                        field -> field,
                        field -> metaData.get(field).asText()
                ));

        // Fetch all URLs in parallel
        return urls.entrySet().parallelStream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> fetchJson(client, mapper, entry.getValue())
                ));
    }

    // Helper method to fetch a single URL and return JsonNode
    private static JsonNode fetchJson(HttpClient client, ObjectMapper mapper, String url) {
        try {
            var request = HttpRequest.newBuilder(URI.create(url))
                    .header("Accept", "application/vnd.github.v3+json")
                    .build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return mapper.readTree(response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to fetch URL: " + url, e);
        }
    }




}
