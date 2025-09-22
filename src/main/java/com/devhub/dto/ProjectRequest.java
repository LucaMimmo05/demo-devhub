package com.devhub.dto;

import com.devhub.models.project.FolderColor;
import com.devhub.models.task.Status;

public class ProjectRequest {
    public String name;
    public String description;
    public int progress;
    public Status status;
    public String technologies;
    public String notes;
    public FolderColor folderColor;

    public ProjectRequest(String name, String description, int progress, Status status, String technologies, String notes, FolderColor folderColor ) {
        this.name = name;
        this.description = description;
        this.progress = progress;
        this.status = status;
        this.technologies = technologies;
        this.notes = notes;
        this.folderColor = folderColor;
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
}
