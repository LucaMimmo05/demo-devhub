package com.devhub.controllers;

import com.devhub.models.GitHubToken;
import com.devhub.models.GithubUser;
import com.devhub.services.GithubAPIService;
import io.vertx.core.json.JsonArray;
import jakarta.inject.Inject;
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

    @Inject
    GithubAPIService githubAPIService;



    @GET
    @Produces
    @Path("/{id}")
    public GitHubToken getGitHubToken(@PathParam("id") Long userId) {
        return GitHubToken.findByUserId(userId);
    }

    @GET
    @Path("/user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGitHubUser(
            @PathParam("userId") Long userId
    ) {


        try {
            GithubUser userInfo = githubAPIService.getUserInfo(userId);
            return Response.ok(userInfo).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (InternalServerErrorException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/repos/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserRepos(
            @PathParam("userId") Long userId,
            @QueryParam("sort") @DefaultValue("updated") String sort,
            @QueryParam("per_page") @DefaultValue("10") int perPage
    ) {
        JsonArray jsonArray;
        try {
            jsonArray = githubAPIService.getAllRepos(userId, sort, perPage);
            return Response.ok(jsonArray).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (InternalServerErrorException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }


    }


}
