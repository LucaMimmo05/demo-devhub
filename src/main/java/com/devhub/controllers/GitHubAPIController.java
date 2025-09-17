package com.devhub.controllers;

import com.devhub.models.GitHubToken;
import io.vertx.core.json.JsonArray;
import jakarta.ws.rs.*;
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
    @Produces
    @Path("/{id}")
    public GitHubToken getGitHubToken(@PathParam("id") Long userId) {
        return GitHubToken.findByUserId(userId);
    }

    @GET
    @Path("/repos/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserRepos(
            @PathParam("userId") Long userId,
            @QueryParam("sort") @DefaultValue("updated") String sort,
            @QueryParam("per_page") @DefaultValue("10") int perPage
    ) {
        GitHubToken token = GitHubToken.findByUserId(userId);
        if (token == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "No GitHub token found"))
                    .build();
        }

        try {
            HttpClient client = HttpClient.newHttpClient();
            String url = String.format("https://api.github.com/user/repos?sort=%s&per_page=%d", sort, perPage);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "token " + token.accessToken)
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonArray repos = new JsonArray(response.body());
                return Response.ok(repos).build();
            } else {
                return Response.status(Response.Status.BAD_GATEWAY)
                        .entity(Map.of(
                                "error", "GitHub API error",
                                "status", response.statusCode(),
                                "body", response.body()
                        ))
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", e.getMessage() != null ? e.getMessage() : "Unknown error"))
                    .build();
        }
    }


}
