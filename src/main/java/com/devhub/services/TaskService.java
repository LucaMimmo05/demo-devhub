package com.devhub.services;

import com.devhub.models.Task;
import com.devhub.repositories.TaskRepository;
import com.devhub.dto.TaskResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class TaskService {

    @Inject
    TaskRepository taskRepository;

    public List<TaskResponse> getTasksByUserId(Long userId) {
        return Task.list("user.id", userId)
                .stream()
                .map(t -> ((Task) t).toDTO())
                .collect(Collectors.toList());
    }




}
