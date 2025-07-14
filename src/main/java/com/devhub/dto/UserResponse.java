package com.devhub.dto;

import com.devhub.models.role.Role;

public class UserResponse {
    private Long id;
    private String email;
    private String name;
    private String surname;
    private Role role;

    public UserResponse(Long id, String email, String name, String surname, Role role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Role getRole() {
        return role;
    }
}
