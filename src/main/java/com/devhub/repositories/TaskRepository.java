package com.devhub.repositories;

import com.devhub.models.Task;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class TaskRepository {

    public List<Task> getTasksByUserId(Long userId) {
        return Task.list("user.id", userId);
    }
}
