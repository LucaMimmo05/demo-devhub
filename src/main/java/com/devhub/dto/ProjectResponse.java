package com.devhub.dto;

import com.devhub.models.project.FolderColor;
import com.devhub.models.task.Status;

public class ProjectResponse {
    public String name;
    public String description;
    public int progress;
    public Status status;
    public String technologies;
    public String notes;
    public UserSummary user;
    public FolderColor folderColor;

    public ProjectResponse() {
    }

    public ProjectResponse(String name, String description, int progress, Status status, String technologies, String notes, UserSummary user, FolderColor folderColor) {
        this.name = name;
        this.description = description;
        this.progress = progress;
        this.status = status;
        this.technologies = technologies;
        this.notes = notes;
        this.user = user;
        this.folderColor = folderColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTechnologies() {
        return technologies;
    }

    public void setTechnologies(String technologies) {
        this.technologies = technologies;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public UserSummary getUser() {
        return user;
    }

    public void setUser(UserSummary user) {
        this.user = user;
    }

    public FolderColor getFolderColor() {
        return folderColor;
    }
    public void setFolderColor(FolderColor folderColor) {
        this.folderColor = folderColor;
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
