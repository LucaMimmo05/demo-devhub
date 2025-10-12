package com.devhub.models;

import com.devhub.dto.CommandResponse;
import com.devhub.dto.TaskResponse;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "commands")
public class Command extends PanacheEntity {
    @JsonbProperty
    @Column(name = "title", nullable = false)
    private String title;

    @JsonbProperty
    @Column(name = "command_text", nullable = false)
    private String commandText;

    @JsonbProperty
    @Column(name = "description", nullable = false)
    private String description;

    @JsonbProperty
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Command() {
    }

    public Command(String title, String commandText, String description, LocalDateTime createdAt, User user) {
        this.title = title;
        this.commandText = commandText;
        this.description = description;
        this.createdAt = createdAt;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CommandResponse toDTO() {
        CommandResponse cr = new CommandResponse();
        cr.setTitle(this.title);
        cr.setCommandText(this.commandText);
        cr.setDescription(this.description);
        cr.setCreatedAt(this.createdAt);


        if (Objects.nonNull(this.user)) {
            CommandResponse.UserSummary us = new CommandResponse.UserSummary();
            us.setId(this.user.id);
            us.setName(this.user.getName());
            us.setEmail(this.user.getEmail());
            cr.setUser(us);
        }

        return cr;
    }
}
