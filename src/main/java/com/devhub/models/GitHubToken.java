package com.devhub.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class GitHubToken extends PanacheEntity {

    @Column(nullable = false, unique = true)
    public Long userId;

    @Column(nullable = false, length = 512)
    public String accessToken;

    public static GitHubToken findByUserId(Long userId) {
        return find("userId", userId).firstResult();
    }
}
