package com.devhub.controllers;

import com.devhub.dto.GitHubOAuthRequest;
import com.devhub.models.GitHubToken;
import io.vertx.core.json.JsonObject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Path("/github")
public class GitHubOAuthController {

    @ConfigProperty(name = "github.client.id")
    private String CLIENT_ID;

    @ConfigProperty(name = "github.client.secret")
    private String CLIENT_SECRET;

    @ConfigProperty(name = "github.redirect.uri")
    private String REDIRECT_URI;

    @POST
    @Path("/callback")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response githubCallBack(GitHubOAuthRequest request) {

        // Validazione input
        if (request == null || request.getCode() == null || request.getUserId() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "Missing code or userId"))
                    .build();
        }

        String code = request.getCode();
        Long userId = request.getUserId();


        try {
            // Body della richiesta a GitHub
            String requestBody = "client_id=" + CLIENT_ID +
                    "&client_secret=" + CLIENT_SECRET +
                    "&code=" + URLEncoder.encode(code, StandardCharsets.UTF_8) +
                    "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8);

            // Chiamata HTTP a GitHub
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://github.com/login/oauth/access_token"))
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());


            JsonObject json = new JsonObject(response.body());
            String accessToken = json.getString("access_token");

            if (accessToken == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("error", "Failed to retrieve access token"))
                        .build();
            }

            // Salvataggio token nel DB
            GitHubToken token = GitHubToken.findByUserId(userId);
            if (token == null) {
                token = new GitHubToken();
                token.setUserId(userId) ;
            }
            token.setAccessToken(accessToken);
            token.persist();

            // Risposta al frontend
            return Response.ok(Map.of("accessToken", accessToken)).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }
}
