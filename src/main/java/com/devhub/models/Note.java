package com.devhub.models;

import com.devhub.dto.NoteResponse;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;
import java.util.Objects;
@Entity(name = "notes")
public class Note extends PanacheEntity {

    @JsonbProperty
    @Column(name = "title", nullable = false)
    private String title;

    @JsonbProperty
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

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

    public Note(String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt, User user) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
    }

    public Note() {
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public NoteResponse toDTO() {
        NoteResponse nr = new NoteResponse();
        nr.setId(this.id);
        nr.setTitle(this.title);
        nr.setContent(this.content);
        nr.setCreatedAt(this.createdAt);
        nr.setUpdatedAt(this.updatedAt);

        if (Objects.nonNull(this.user)) {
            NoteResponse.UserSummary us = new NoteResponse.UserSummary();
            us.setId(this.user.id);
            us.setName(this.user.getName());
            us.setEmail(this.user.getEmail());
            nr.setUser(us);
        }

        return nr;
    }
}
