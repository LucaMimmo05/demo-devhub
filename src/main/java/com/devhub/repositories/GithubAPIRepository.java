package com.devhub.repositories;

import com.devhub.models.GitHubToken;
import com.devhub.models.GithubUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.json.JsonArray;
import jakarta.enterprise.context.ApplicationScoped;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@ApplicationScoped
public class GithubAPIRepository {

    private static final String API_BASE_URL = "https://api.github.com";

    public GitHubToken getGithubTokenByUserId(Long userId) {
        return GitHubToken.findByUserId(userId);
    }

    public JsonArray getAllRepos(GitHubToken token, String sort, int perPage) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String url = String.format(API_BASE_URL + "/user/repos?sort=%s&per_page=%d", sort, perPage);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "token " + token.getAccessToken())
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return new JsonArray(response.body());
        }
        throw new Exception("GitHub API error: " + response.body());
    }

    public GithubUser getUserInfo(GitHubToken token) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String url = API_BASE_URL + "/user";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "token " + token.getAccessToken())
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.body(), GithubUser.class);
        }
        throw new Exception("GitHub API error: " + response.body());
    }

    public String getGithubUsername(GitHubToken token) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String url = API_BASE_URL + "/user";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "token " + token.getAccessToken())
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            ObjectMapper mapper = new ObjectMapper();
            GithubUser githubUser = mapper.readValue(response.body(), GithubUser.class);
            return githubUser.getLogin();
        }
        throw new Exception("GitHub API error: " + response.body());
    }

    public JsonArray getUserRecentActivities(GitHubToken token, String username, int perPage) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String url = String.format(API_BASE_URL + "/users/%s/events?per_page=%d", username, perPage);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "token " + token.getAccessToken())
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return new JsonArray(response.body());
        }
        throw new Exception("GitHub API error: " + response.body());
    }
}
