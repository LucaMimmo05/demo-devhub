package com.devhub.dto;

import com.devhub.models.project.FolderColor;
import com.devhub.models.task.Status;

import java.time.LocalDateTime;

public class ProjectRequest {
    public String name;
    public String description;
    public int progress;
    public Status status;
    public String technologies;
    public String notes;
    public FolderColor folderColor;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    public ProjectRequest(String name, String description, int progress, Status status, String technologies, String notes, FolderColor folderColor, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.name = name;
        this.description = description;
        this.progress = progress;
        this.status = status;
        this.technologies = technologies;
        this.notes = notes;
        this.folderColor = folderColor;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ProjectRequest() {
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

    public FolderColor getFolderColor() {
        return folderColor;
    }

    public void setFolderColor(FolderColor folderColor) {
        this.folderColor = folderColor;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
