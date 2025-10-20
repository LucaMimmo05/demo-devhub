package com.devhub.dto;

import java.time.LocalDateTime;

public class CommandResponse {

    public String title;
    public String commandText;
    public String description;
    public LocalDateTime createdAt;
    public UserSummary user;
    public String example;

    public CommandResponse() {
    }

    public CommandResponse(String title, String commandText, String description, LocalDateTime createdAt, UserSummary user, String example) {
        this.title = title;
        this.commandText = commandText;
        this.description = description;
        this.createdAt = createdAt;
        this.user = user;
        this.example = example;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCommandText() {
        return commandText;
    }

    public void setCommandText(String commandText) {
        this.commandText = commandText;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserSummary getUser() {
        return user;
    }

    public void setUser(UserSummary user) {
        this.user = user;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public static class UserSummary {
        private Long id;
        private String name;
        private String email;

        public Long getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }

        public void setId(Long id) { this.id = id; }
        public void setName(String name) { this.name = name; }
        public void setEmail(String email) { this.email = email; }
    }
}
