package com.devhub.models;

import com.devhub.dto.TaskResponse;
import com.devhub.models.task.TaskPriority;
import com.devhub.models.task.Status;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tasks")
public class Task extends PanacheEntity {

    @JsonbProperty
    @Column(name = "title", nullable = false)
    private String title;

    @JsonbProperty
    @Column(name = "description")
    private String description;

    @JsonbProperty
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @JsonbProperty
    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private TaskPriority priority;

    @JsonbProperty
    @JsonbDateFormat("yyyy-MM-dd")
    @Column(name = "due_date")
    private LocalDate dueDate;

    @JsonbProperty
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonbProperty
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonbProperty
    private LocalDateTime completedAt;

    public Task() {}

    public Task(String title, String description, Status status, TaskPriority priority,
                LocalDate dueDate, LocalDateTime createdAt, LocalDateTime updatedAt, User user, LocalDateTime completedAt) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
        this.completedAt = completedAt;
    }

    // Getter & Setter
    public Long getId() { return id; } // ereditato da PanacheEntity, aggiungiamo getter per coerenza
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public TaskPriority getPriority() { return priority; }
    public void setPriority(TaskPriority priority) { this.priority = priority; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }

    // Conversione in DTO
    public TaskResponse toDTO() {
        TaskResponse tr = new TaskResponse();
        tr.setId(this.id);
        tr.setTitle(this.title);
        tr.setDescription(this.description);
        tr.setStatus(this.status);
        tr.setPriority(this.priority);
        tr.setDueDate(this.dueDate);
        tr.setCreatedAt(this.createdAt);
        tr.setUpdatedAt(this.updatedAt);

        if (Objects.nonNull(this.user)) {
            TaskResponse.UserSummary us = new TaskResponse.UserSummary();
            us.setId(this.user.id);
            us.setName(this.user.getName());
            us.setEmail(this.user.getEmail());
            tr.setUser(us);
        }

        return tr;
    }
}
