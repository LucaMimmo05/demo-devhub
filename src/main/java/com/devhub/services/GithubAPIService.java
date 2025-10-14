package com.devhub.services;

import com.devhub.exception.NotFoundException;
import com.devhub.models.GitHubToken;
import com.devhub.models.GithubUser;
import com.devhub.repositories.GithubAPIRepository;
import io.vertx.core.json.JsonArray;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.InternalServerErrorException;

@ApplicationScoped
public class GithubAPIService {

    @Inject
    GithubAPIRepository githubAPIRepository;

    public JsonArray getAllRepos(Long userId, String sort, int perPage) {
        GitHubToken token = githubAPIRepository.getGithubTokenByUserId(userId);
        if (token == null) {
            throw new NotFoundException("GitHub token not found for user ID: " + userId);
        }

        try {
            return githubAPIRepository.getAllRepos(token, sort, perPage);
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to fetch repositories: " + e.getMessage());
        }
    }

    public GitHubToken getGitHubToken(Long userId) {
        GitHubToken token = githubAPIRepository.getGithubTokenByUserId(userId);
        if (token == null) {
            throw new NotFoundException("GitHub token not found for user ID: " + userId);
        }
        return token;
    }

    public GithubUser getUserInfo(Long userId) {
        GitHubToken token = githubAPIRepository.getGithubTokenByUserId(userId);
        if (token == null) {
            throw new NotFoundException("GitHub token not found for user ID: " + userId);
        }

        try {
            return githubAPIRepository.getUserInfo(token);
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to fetch user info: " + e.getMessage());
        }
    }

    public JsonArray getUserRecentActivities(Long userId, int perPage) {
        GitHubToken token = githubAPIRepository.getGithubTokenByUserId(userId);
        if (token == null) {
            throw new NotFoundException("GitHub token not found for user ID: " + userId);
        }

        try {
            String username = githubAPIRepository.getGithubUsername(token);
            return githubAPIRepository.getUserRecentActivities(token, username, perPage);
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to fetch recent activities: " + e.getMessage());
        }
    }
}
