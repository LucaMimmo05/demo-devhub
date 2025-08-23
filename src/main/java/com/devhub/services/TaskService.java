package com.devhub.services;

import com.devhub.dto.TaskRequest;
import com.devhub.dto.UserResponse;
import com.devhub.models.Task;
import com.devhub.repositories.TaskRepository;
import com.devhub.dto.TaskResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class TaskService {

    @Inject
    TaskRepository taskRepository;

    public List<TaskResponse> getTasksByUserId(Long userId) {
        return taskRepository.getTasksByUserId(userId);
    }

    public TaskResponse createTask(TaskRequest task, Long userId) {
        return taskRepository.createTask(task , userId);
    }

    public TaskResponse updateTask(Long taskId, TaskRequest task, UserResponse currentUser) {
        return taskRepository.updateTask(taskId, task, currentUser);
    }


    public void deleteTask(Long userId,Long taskId) {
        taskRepository.deleteTask(userId,taskId);
    }



}
