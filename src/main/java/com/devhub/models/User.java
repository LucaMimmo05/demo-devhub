package com.devhub.models;

import com.devhub.models.role.Role;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends PanacheEntity {




    @JsonbProperty
    @Column(name = "name", nullable = false)
    public String name;

    @JsonbProperty
    @Column(name = "surname", nullable = false)
    public String surname;

    @JsonbProperty
    @Column(name = "email", nullable = false)
    public String email;

    @Column(name = "password", nullable = false)
    public String password;

    @JsonbProperty
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "created_at")
    public LocalDateTime createdAt;


    @Enumerated(EnumType.STRING)
    @JsonbProperty
    private Role role;


    public User(String name, String surname, String email, String password, Role role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.role = role;
    }

    public User() {
    }

    public static User findByEmail(String email) {
        return find("email", email).firstResult();
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
