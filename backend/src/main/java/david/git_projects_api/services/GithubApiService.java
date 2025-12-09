package david.git_projects_api.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import david.git_projects_api.domain.ApiKey;
import david.git_projects_api.dtos.ProjectsRequest;
import david.git_projects_api.exceptions.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class GithubApiService {
    private static final String githubToken = System.getenv("Github_Token");

    public ArrayList<ObjectNode> handleGithubFetches(ApiKey apiKey) throws IOException, InterruptedException {
       ArrayList<ObjectNode> fullDataList = new ArrayList<>();
       String githubUsername = apiKey.getUser().getUsername();


       List<String> repos=  getAllRepos(githubUsername);
        repos.removeAll(apiKey.getBlacklist());

       for(String repoName:repos){
        String repoUrl = buildGithubUrl(repoName, githubUsername);
        String lastBranch =fetchLastBranch(repoUrl);
        ObjectNode fullData =  extractRepoSummary(githubUsername,repoName,lastBranch);
        fullDataList.add(fullData);
       }

        return fullDataList;
    }

    private List<String> getAllRepos(String username) throws IOException, InterruptedException {
        log.info("fetching all repos for: "+username);
        ObjectMapper mapper = new ObjectMapper();
        HttpClient client = HttpClient.newHttpClient();

        // Added ?per_page=100 to get more results (default is 30)
        String url = String.format("https://api.github.com/users/%s/repos?per_page=100", username);

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .GET()
                .header("Authorization", "token " + githubToken)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonNode rootNode = mapper.readTree(response.body());
        List<String> repoNames = new ArrayList<>();

        // Check if the response is an array (it should be)
        if (rootNode.isArray()) {
            for (JsonNode repo : rootNode) {
                // Extract the "name" field and add to list
                if (repo.has("name")) {
                    repoNames.add(repo.get("name").asText());
                }
            }
        }

        return repoNames;
    }

    public static String buildGithubUrl(String repoName, String githubUsername) {
        String baseApiUrl = "https://api.github.com/repos/" + githubUsername;
        return baseApiUrl + "/" + repoName;
    }

    public static String fetchLastBranch(String url){
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();
        HttpRequest branchRequest = HttpRequest.newBuilder(URI.create(url + "/branches")).GET()
                .header("Authorization", "token " +githubToken).build();
        try {
            HttpResponse<String> branchResponse = client.send(branchRequest, HttpResponse.BodyHandlers.ofString());
            JsonNode branchJson = mapper.readTree(branchResponse.body());
            return branchJson.get(branchJson.size() - 1)
                    .get("name")
                    .asText();
        } catch (InterruptedException | IOException e) {
            throw new ApiException("There was a problem with the github branch request and parsing if it's result ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static ObjectNode extractRepoSummary(String owner, String repoName, String branch) {
        var client = HttpClient.newHttpClient();
        var mapper = new ObjectMapper();
        var root = mapper.createObjectNode();

        String base = "https://api.github.com/repos/" + owner + "/" + repoName;
//repo url
        JsonNode repoUrl = root.put("repoUrl",base);
        // A: Basic repo info
        JsonNode info = fetchJson(client, mapper, base);
        root.put("repo", repoName);
        root.put("description", info.path("description").asText(null));

        // B1: Languages
        JsonNode languages = fetchJson(client, mapper, base + "/languages");
        root.set("languages", languages);

        // B2: Full file tree
        JsonNode tree = fetchJson(client, mapper, base + "/git/trees/" + branch + "?recursive=1");
        ArrayNode files = mapper.createArrayNode();
        tree.path("tree").forEach(node -> {
            if (node.has("path")) files.add(node.get("path").asText());
        });
        root.set("tree", files);

        // Optional: README summary placeholder (we can add real summarization next)
        JsonNode readme = fetchJson(client, mapper, base + "/readme");
        if (readme.has("content")) {
            String base64 = readme.get("content").asText();
            base64 = base64.replaceAll("\\s+", "");
            byte[] decodedBytes = java.util.Base64.getDecoder().decode(base64);
            String readmeMd = new String(decodedBytes, java.nio.charset.StandardCharsets.UTF_8);
            root.put("readme", readmeMd);
        } else {
            root.put("readme", "No README found");
        }

        return root;
    }

    private static JsonNode fetchJson(HttpClient client, ObjectMapper mapper, String url) {
        try {
            var request = HttpRequest.newBuilder(URI.create(url))
                    .header("Accept", "application/vnd.github.v3+json")
                    .header("Authorization", "token " +githubToken)
                    .build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return mapper.readTree(response.body());
        } catch (Exception e) {
            return mapper.createObjectNode().put("error", "Failed to fetch: " + url);
        }
    }




}
