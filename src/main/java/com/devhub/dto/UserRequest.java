package com.devhub.dto;

public class UserRequest {
    public String name;
    public String surname;
    public String password;

    public UserRequest(String name,String surname, String password) {
        this.name = name;
        this.surname = surname;
        this.password = password;
    }

    public UserRequest() {}

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
