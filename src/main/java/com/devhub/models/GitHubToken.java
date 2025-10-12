package com.devhub.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class GitHubToken extends PanacheEntity {

    @Column(nullable = false, unique = true)
    private Long userId;

    @Column(nullable = false, length = 512)
    private String accessToken;

    public GitHubToken(Long userId, String accessToken) {
        this.userId = userId;
        this.accessToken = accessToken;
    }
    public GitHubToken() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public static GitHubToken findByUserId(Long userId) {
        return find("userId", userId).firstResult();
    }
}
