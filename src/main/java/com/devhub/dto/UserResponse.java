package com.devhub.dto;

public class UserResponse {
    private Long id;
    private String email;
    private String name;
    private String surname;
    private String role;

    public UserResponse(Long id, String email, String name, String surname, String role) {
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

    public String getRole() {
        return role;
    }
}
