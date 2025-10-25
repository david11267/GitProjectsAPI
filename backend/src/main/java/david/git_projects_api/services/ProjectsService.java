package david.git_projects_api.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import david.git_projects_api.domain.User;
import david.git_projects_api.dtos.ProjectsRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.UUID;
@Service
public class ProjectsService {
    private  final UserService userService;
    public ProjectsService(UserService userService) {
        this.userService = userService;
    }

    public void handleProjectsRequest(ProjectsRequest request) throws IOException, InterruptedException {
        System.out.println("Received request from API key: " + request.apiKey());
        System.out.println("Repos: " + request.repos());
        User user =userService.getOrCreateUser(request.apiKey());
        ArrayList<String> githubUrls= buildGithubUrls(request, user.getUsername());
        JsonNode result = fetchUrlData(githubUrls.get(0));
    }


    private ArrayList<String> buildGithubUrls(ProjectsRequest request, String githubUsername) {
        String baseApiUrl = "https://api.github.com/repos/" + githubUsername;
        ArrayList<String> result = new ArrayList<>();
        for (String repo: request.repos()){
            var url = baseApiUrl + "/" + repo;
            result.add(url);
        }
        return  result;
    }
    private JsonNode fetchUrlData(String url) throws IOException, InterruptedException {
        var client = java.net.http.HttpClient.newHttpClient();
        var httpRequest = java.net.http.HttpRequest.newBuilder(
                        java.net.URI.create(url))
                .header("Accept", "application/vnd.github.v3+json")
                .build();

        var response = client.send(httpRequest, java.net.http.HttpResponse.BodyHandlers.ofString());
        var mapper = new ObjectMapper();

        JsonNode json = mapper.readTree(response.body());
        System.out.println(json.toPrettyString()); // nicely formatted JSON

        return json;
    }
}


