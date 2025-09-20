package com.devhub.models;

import com.devhub.dto.CommandResponse;
import com.devhub.dto.ProjectResponse;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "projects")
public class Project extends PanacheEntity {
    @JsonbProperty
    @JoinColumn(name = "user_id")
    @ManyToOne
    public User user;

    @JsonbProperty
    @Column(name = "name")
    public String name;

    @JsonbProperty
    @Column(name = "description")
    public String description;

    @JsonbProperty
    @Column(name = "progress")
    public int progress;

    @JsonbProperty
    @Column(name = "priority")
    public String priority;

    @JsonbProperty
    @Column(name = "technologies")
    public String technologies;

    @JsonbProperty
    @Column(name = "notes")
    public String notes;

    @JsonbProperty
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @JsonbProperty
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "updated_at")
    public LocalDateTime updatedAt;


    public Project(User user, String name, String description, int progress, String priority, String technologies, String notes) {
        this.user = user;
        this.name = name;
        this.description = description;
        this.progress = progress;
        this.priority = priority;
        this.technologies = technologies;
        this.notes = notes;
    }

    public Project() {}

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

    public ProjectResponse toDTO() {
        ProjectResponse pr = new ProjectResponse();
        pr.setName(this.name);
        pr.setDescription(this.description);
        pr.setProgress(this.progress);
        pr.setPriority(this.priority);
        pr.setTechnologies(this.technologies);
        pr.setNotes(this.notes);

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
