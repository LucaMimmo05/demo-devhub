package com.devhub.controllers;

import com.devhub.models.GitHubToken;
import com.devhub.models.GithubUser;
import com.devhub.services.GithubAPIService;
import io.vertx.core.json.JsonArray;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.Map;

@Path("/github")
@Produces(MediaType.APPLICATION_JSON)
public class GitHubAPIController {

    @Inject
    GithubAPIService githubAPIService;

    @Inject
    Instance<JsonWebToken> jwtInstance;

    public Long getCurrentUserId() {
        JsonWebToken jwt = jwtInstance.get();
        return Long.valueOf(jwt.getSubject());
    }

    @GET
    public Response getGitHubToken() {
        Long userId = getCurrentUserId();
        GitHubToken token = githubAPIService.getGitHubToken(userId);
        if (token == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "GitHub token not found for user ID: " + userId))
                    .build();
        }
        return Response.ok(token).build();
    }

    @GET
    @Path("/user")
    public GithubUser getGitHubUser() {
        Long userId = getCurrentUserId();
        return githubAPIService.getUserInfo(userId);
    }

    @GET
    @Path("/repos")
    public JsonArray getUserRepos(
            @QueryParam("sort") @DefaultValue("updated") String sort,
            @QueryParam("per_page") @DefaultValue("10") int perPage
    ) {
        Long userId = getCurrentUserId();
        return githubAPIService.getAllRepos(userId, sort, perPage);
    }

    @GET
    @Path("/activities")
    public JsonArray getUserRecentActivities(
            @QueryParam("per_page") @DefaultValue("10") int perPage
    ) {
        Long userId = getCurrentUserId();
        return githubAPIService.getUserRecentActivities(userId, perPage);
    }
}
