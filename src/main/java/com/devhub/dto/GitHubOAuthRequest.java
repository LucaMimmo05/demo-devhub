package com.devhub.dto;

public class GitHubOAuthRequest {
    public String code;
    public Long userId;

    public GitHubOAuthRequest(String code, Long userId) {
        this.code = code;
        this.userId = userId;
    }

    public GitHubOAuthRequest() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
