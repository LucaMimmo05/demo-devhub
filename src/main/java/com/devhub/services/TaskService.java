package com.devhub.services;

import com.devhub.dto.TaskRequest;
import com.devhub.dto.UserResponse;
import com.devhub.repositories.TaskRepository;
import com.devhub.dto.TaskResponse;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class TaskService {

    @Inject
    TaskRepository taskRepository;

    public List<TaskResponse> getTasksByUserId(Long userId) {
        return taskRepository.getTasksByUserId(userId);
    }

    public List<TaskResponse> getNotCompletedTasksByUserId(Long userId) {
        return taskRepository.getNotCompletedTasksByUserId(userId);
    }

    public List<TaskResponse> getAllCompletedTasksByUserId(Long userId) {
        return taskRepository.getAllCompletedTasksByUserId(userId);
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

    public TaskResponse completeTask(Long userId, Long taskId) {
        return taskRepository.completeTask(userId, taskId);
    }

    @Scheduled(every="24h")
    void deleteOldCompletedTasks() {
        taskRepository.deleteOldCompletedTasks();
    }




}
