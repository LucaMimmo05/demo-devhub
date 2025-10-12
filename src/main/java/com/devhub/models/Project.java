package com.devhub.models;

import com.devhub.dto.ProjectResponse;
import com.devhub.models.project.FolderColor;
import com.devhub.models.task.Status;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.List;


@Entity
@Table(name = "projects")
public class Project extends PanacheEntity {
    @JsonbProperty
    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    private User user;

    @JsonbProperty
    @Column(name = "name", nullable = false)
    private String name;

    @JsonbProperty
    @Column(name = "description")
    private String description;

    @JsonbProperty
    @Column(name = "progress" , nullable = false)
    private int progress;

    @Enumerated(EnumType.STRING)
    @JsonbProperty
    @Column(name = "status", nullable = false)
    private Status status;

    @JsonbProperty
    @Column(name = "technologies")
    private List<String> technologies;

    @JsonbProperty
    @Column(name = "notes")
    private String notes;

    @JsonbProperty
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonbProperty
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonbProperty
    @Enumerated(EnumType.STRING)
    @Column(name = "folder_color", nullable = false)
    private FolderColor folderColor;



    public Project(User user, String name, String description, int progress, Status status, List<String> technologies, String notes, FolderColor folderColor, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.user = user;
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

    public Project() {
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public List<String> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<String> technologies) {
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

    public ProjectResponse toDTO() {
        ProjectResponse pr = new ProjectResponse();
        pr.setId(this.id);
        pr.setName(this.name);
        pr.setDescription(this.description);
        pr.setProgress(this.progress);
        pr.setStatus(this.status);
        pr.setTechnologies(this.technologies);
        pr.setNotes(this.notes);
        pr.setFolderColor(this.folderColor);
        pr.setCreatedAt(this.createdAt);
        pr.setUpdatedAt(this.updatedAt);

        if (Objects.nonNull(this.user)) {
            ProjectResponse.UserSummary us = new ProjectResponse.UserSummary();
            us.setId(this.user.id);
            us.setName(this.user.getName());
            us.setEmail(this.user.getEmail());
            pr.setUser(us);
        }
        return pr;
    }
}
