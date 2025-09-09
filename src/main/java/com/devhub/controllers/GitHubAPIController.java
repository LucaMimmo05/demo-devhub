package com.devhub.controllers;

import com.devhub.models.GitHubToken;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Path("/github")
public class GitHubAPIController {

    @GET
    @Path("/repos/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserRepos(@PathParam("userId") Long userId) {
        GitHubToken token = GitHubToken.findByUserId(userId);
        if (token == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "No GitHub token found"))
                    .build();
        }

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.github.com/user/repos"))
                    .header("Authorization", "token " + token.accessToken)
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return Response.ok(response.body()).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }
}
