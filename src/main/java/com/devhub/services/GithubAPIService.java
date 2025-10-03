package com.devhub.services;

import com.devhub.models.GithubUser;
import com.devhub.repositories.GithubAPIRepository;
import io.vertx.core.json.JsonArray;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class GithubAPIService {

    @Inject
    GithubAPIRepository githubAPIRepository;

    public JsonArray getAllRepos(Long userId, String sort, int perPage) {
        return githubAPIRepository.getAllRepos(userId, sort, perPage);
    }

    public GithubUser getUserInfo(Long userId) {
        return githubAPIRepository.getUserInfo(userId);
    }
}
