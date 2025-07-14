package com.devhub.repositories;


import com.devhub.dto.LoginRequest;
import com.devhub.dto.RegisterRequest;
import com.devhub.models.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;


@ApplicationScoped
public class UserRepository {
    public User findUserByEmail(String email) {
        return User.findByEmail(email);
    }

    public User checkExistingUser(RegisterRequest request) {
        return User.findByEmail(request.getEmail());
    }
    @Transactional
    public void createUser(User newUser) {
        newUser.persist();
    }

}
