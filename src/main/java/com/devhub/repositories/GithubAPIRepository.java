package com.devhub.repositories;

import com.devhub.models.GitHubToken;
import com.devhub.models.GithubUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.json.JsonArray;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
@ApplicationScoped
public class GithubAPIRepository {

     final String API_BASE_URL = "https://api.github.com";


    public JsonArray getAllRepos( Long userId, String sort, int perPage) {

        GitHubToken token = GitHubToken.findByUserId(userId);
        if (token == null) {
            throw new NotFoundException("GitHub token not found for user ID: " + userId);
        }

        try {
            HttpClient client = HttpClient.newHttpClient();
            String url = String.format(API_BASE_URL +"/user/repos?sort=%s&per_page=%d", sort, perPage);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "token " + token.accessToken)
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonArray repos = new JsonArray(response.body());
                return repos;
            } else {
                throw new Exception("Failed to fetch repositories: " + response.body());
            }
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to fetch repositories: " + e.getMessage());
        }
    }


    public GithubUser getUserInfo(Long userId) {
        GitHubToken token = GitHubToken.findByUserId(userId);

        try {
            HttpClient client = HttpClient.newHttpClient();
            String url = API_BASE_URL + "/user";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "token " + token.accessToken)
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();

                return mapper.readValue(response.body(), GithubUser.class);
            } else {
                throw new Exception("Failed to fetch user info: " + response.body());
            }
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to fetch user info: " + e.getMessage());
        }
    }

    public String getGithubUsername(Long userId) {
        GitHubToken token = GitHubToken.findByUserId(userId);
        if (token == null) {
            throw new NotFoundException("GitHub token not found for user ID: " + userId);
        }

        try {
            HttpClient client = HttpClient.newHttpClient();
            String url = API_BASE_URL + "/user";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "token " + token.accessToken)
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                GithubUser githubUser = mapper.readValue(response.body(), GithubUser.class);
                return githubUser.getLogin();
            } else {
                throw new Exception("Failed to fetch user info: " + response.body());
            }
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to fetch user info: " + e.getMessage());
        }
    }

    public JsonArray getUserRecentActivities (Long userId, int perPage) {
        GitHubToken token = GitHubToken.findByUserId(userId);
        if (token == null) {
            throw new NotFoundException("GitHub token not found for user ID: " + userId);
        }

        try {

            HttpClient client = HttpClient.newHttpClient();
            String url = String.format(API_BASE_URL +"/users/"+ getGithubUsername(userId)+"/events?per_page=%d", perPage);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "token " + token.accessToken)
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return new JsonArray(response.body());
            } else {
                throw new Exception("Failed to fetch recent activities: " + response.body());
            }
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to fetch recent activities: " + e.getMessage());
        }
    }
}
