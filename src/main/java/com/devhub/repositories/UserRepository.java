package com.devhub.repositories;


import com.devhub.dto.RegisterRequest;
import com.devhub.dto.UserRequest;
import com.devhub.models.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;


@ApplicationScoped
public class UserRepository {
    public User findUserByEmail(String email) {
        return User.findByEmail(email);
    }

    public User findUserById(Long userId) {
        return User.findById(userId);
    }

    public User checkExistingUser(RegisterRequest request) {
        return findUserByEmail(request.getEmail());
    }

    @Transactional
    public void createUser(User newUser) {
        newUser.persist();
    }


    @Transactional
    public User update(User user, UserRequest request) {
        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getSurname() != null) {
            user.setSurname(request.getSurname());
        }
        if (request.getPassword() != null) {
            user.setPassword(request.getPassword());
        }
        User managed = User.getEntityManager().merge(user);
        User.getEntityManager().flush();

        return managed;

    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = User.findById(userId);
        if (user != null) {
            user.delete();
        }
    }


    public User getUserById(Long userId) {
        return User.findById(userId);
    }

}
