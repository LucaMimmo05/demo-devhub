package com.devhub.services;

import com.devhub.dto.TaskRequest;
import com.devhub.dto.UserResponse;
import com.devhub.exception.BadRequestException;
import com.devhub.exception.NotFoundException;
import com.devhub.models.Task;
import com.devhub.models.User;
import com.devhub.repositories.TaskRepository;
import com.devhub.dto.TaskResponse;
import com.devhub.repositories.UserRepository;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ForbiddenException;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class TaskService {

    @Inject
    TaskRepository taskRepository;
    @Inject
    UserRepository userRepository;

    public List<TaskResponse> getTasksByUserId(Long userId) {
        return taskRepository.getTasksByUserId(userId)
                .stream()
                .map(Task::toDTO)
                .collect(Collectors.toList());
    }

    public List<TaskResponse> getNotCompletedTasksByUserId(Long userId) {
        return taskRepository.getNotCompletedTasksByUserId(userId)
                .stream()
                .map(t -> ((Task) t).toDTO())
                .collect(Collectors.toList());
    }

    public List<TaskResponse> getAllCompletedTasksByUserId(Long userId) {
        return taskRepository.getAllCompletedTasksByUserId(userId)
                .stream()
                .map(t -> ((Task) t).toDTO())
                .collect(Collectors.toList());
    }

    public TaskResponse createTask(TaskRequest request, Long userId) {
        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new BadRequestException("Title cannot be empty");
        }
        if (request.getStatus() == null) {
            throw new BadRequestException("Status cannot be null");
        }



        if (request.getPriority() == null) {
            throw new BadRequestException("Priority cannot be null");
        }

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return taskRepository.create(request , user).toDTO();
    }

    public TaskResponse updateTask(Long taskId, TaskRequest request, Long currentUserId) {
        Task existingTask = taskRepository.findById(taskId);
        if (existingTask == null) {
            throw new NotFoundException("Task not found");
        }
        if (!existingTask.getUser().id.equals(currentUserId)) {
            throw new BadRequestException("You are not allowed to update this task");
        }
        return taskRepository.update(existingTask, request).toDTO();
    }


    public void deleteTask(Long userId,Long taskId) {
        Task existingTask = taskRepository.findById(taskId);
        if (existingTask == null) {
            throw new jakarta.ws.rs.NotFoundException("Task not found");
        }
        if (!existingTask.getUser().id.equals(userId)) {
            throw new ForbiddenException("You are not allowed to complete this task");
        }
        taskRepository.delete(existingTask);
    }

    public TaskResponse completeTask(Long userId, Long taskId) {
        Task existingTask = taskRepository.findById(taskId);
        if (existingTask == null) {
            throw new NotFoundException("Task not found");
        }
        if (!existingTask.getUser().id.equals(userId)) {
            throw new BadRequestException("You are not allowed to complete this task");
        }
        return taskRepository.complete(existingTask);
    }

    @Scheduled(every="24h")
    void deleteOldCompletedTasks() {
        taskRepository.deleteOldCompletedTasks();
    }




}
