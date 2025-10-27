package david.git_projects_api.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import david.git_projects_api.dtos.GithubMetaData;
import david.git_projects_api.dtos.ProjectsRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GithubApiService {

    public ArrayList<Map<String, JsonNode>> handleGithubFetches(ProjectsRequest request, String githubUsername) throws IOException, InterruptedException {
       ArrayList<Map<String,JsonNode>> fullDataList = new ArrayList<>();

        for(String repoName:request.repos()){
        String repoUrl = buildGithubUrl(repoName, githubUsername);
        GithubMetaData metaData =fetchMetaData(repoUrl);
        Map<String, JsonNode> fullData = fullDataFetch(metaData);
        fullDataList.add(fullData);
       }

        return fullDataList;
    }

    public static String buildGithubUrl(String repoName, String githubUsername) {
        String baseApiUrl = "https://api.github.com/repos/" + githubUsername;
        return baseApiUrl + "/" + repoName;
    }

    public static GithubMetaData fetchMetaData (String url) throws IOException, InterruptedException {
        var client = java.net.http.HttpClient.newHttpClient();
        var mapper = new ObjectMapper();

        var metaRequest = java.net.http.HttpRequest.newBuilder(
                        java.net.URI.create(url))
                .header("Accept", "application/vnd.github.v3+json")
                .build();
        var metaResponse = client.send(metaRequest, java.net.http.HttpResponse.BodyHandlers.ofString());
        JsonNode metaJson = mapper.readTree(metaResponse.body());


        HttpRequest branchRequest = HttpRequest.newBuilder()
                .uri(URI.create(metaJson.get("branches_url").asText().replace("{/branch}","")))
                .GET()
                .build();
        var branchResponse = client.send(branchRequest, java.net.http.HttpResponse.BodyHandlers.ofString());
        JsonNode branchJson = mapper.readTree(branchResponse.body());
        JsonNode lastBranchNode = branchJson.get(branchJson.size() - 1); // last element


        return new GithubMetaData(
                metaJson.get("description").asText(),
                metaJson.get("url").asText(),
                metaJson.get("collaborators_url").asText(),
                metaJson.get("languages_url").asText(),
                metaJson.get("git_commits_url").asText(),
                metaJson.get("trees_url").asText(),
                lastBranchNode.get("name").asText()
        );
    }

    public static Map<String, JsonNode> fullDataFetch(GithubMetaData metaData) {

        var mapper = new ObjectMapper();
        var client = HttpClient.newHttpClient();

        Map<String, String> urlMap = Map.of(
                "collaborators", metaData.collaborators_url(),
                "languages", metaData.languages_url(),
                "commits", metaData.git_commits_url()
        );

        return urlMap.entrySet().parallelStream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            try {
                                var req = HttpRequest.newBuilder(URI.create(entry.getValue()))
                                        .header("Accept", "application/vnd.github.v3+json")
                                        .build();
                                var res = client.send(req, HttpResponse.BodyHandlers.ofString());
                                return mapper.readTree(res.body());
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                ));
    }



}
