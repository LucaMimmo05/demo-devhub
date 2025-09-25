package com.devhub.dto;

import com.devhub.dto.UserResponse;

public class LoginResponse {
    public String message;
    public String accessToken;
    public String refreshToken;
    public UserResponse user;  // nuovo campo

    // Costruttore completo
    public LoginResponse(String message, String accessToken, String refreshToken, UserResponse user) {
        this.message = message;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user = user;
    }

    // Costruttore solo con token
    public LoginResponse(String message, String accessToken, String refreshToken) {
        this.message = message;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    // Costruttore solo con messaggio
    public LoginResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }
}
