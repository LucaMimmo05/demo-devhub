package com.devhub.dto;

import com.devhub.models.task.TaskPriority;
import com.devhub.models.task.Status;

import java.time.LocalDate;

public class TaskRequest {
    private String title;
    private String description;
    private Status status;
    private TaskPriority priority;
    private LocalDate dueDate;

    // Getter e Setter
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
}
