package com.devhub.dto;

import jakarta.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({ "message", "accessToken", "refreshToken" })
public class LoginResponse {
    public String message;
    public String accessToken;
    public String refreshToken;

    public LoginResponse(String message, String accessToken, String refreshToken) {
        this.message = message;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public LoginResponse(String message) {
        this.message = message;
    }
}

