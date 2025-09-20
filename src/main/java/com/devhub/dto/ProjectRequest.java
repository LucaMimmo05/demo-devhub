package com.devhub.dto;

public class ProjectRequest {
    public String name;
    public String description;
    public int progress;
    public String priority;
    public String technologies;
    public String notes;

    public ProjectRequest(String name, String description, int progress, String priority, String technologies, String notes) {
        this.name = name;
        this.description = description;
        this.progress = progress;
        this.priority = priority;
        this.technologies = technologies;
        this.notes = notes;
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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
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
}
