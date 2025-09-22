package com.devhub.repositories;

import com.devhub.dto.TaskRequest;
import com.devhub.dto.TaskResponse;
import com.devhub.dto.UserResponse;
import com.devhub.models.Task;
import com.devhub.models.User;
import com.devhub.models.task.TaskPriority;
import com.devhub.models.task.Status;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class TaskRepository {

    public List<TaskResponse> getTasksByUserId(Long userId) {
        return Task.list("user.id", userId)
                .stream()
                .map(t -> ((Task) t).toDTO())
                .collect(Collectors.toList());
    }
    @Transactional
    public TaskResponse createTask(TaskRequest request, Long userId) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(Status.valueOf(request.getStatus().name()));
        task.setPriority(TaskPriority.valueOf(request.getPriority().name()));
        User user = User.findById(userId);

        if (user == null) {
            throw new WebApplicationException("User not found", 404);
        }
        task.setUser(user);

        task.persist();
        return task.toDTO();
    }

    @Transactional
    public TaskResponse updateTask(Long taskId, TaskRequest task, UserResponse currentUser) {
        Task existingTask = Task.findById(taskId);

        if (existingTask == null) {
            throw new NotFoundException("Task not found");
        }

        // Controllo che la task appartenga all'utente loggato
        if (!existingTask.getUser().id.equals(currentUser.getId())) {
            throw new ForbiddenException("You are not allowed to update this task");
        }

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setStatus(task.getStatus());
        existingTask.setPriority(task.getPriority());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setUpdatedAt(LocalDateTime.now());

        existingTask.persist();

        return existingTask.toDTO();
    }


    @Transactional
    public void deleteTask(Long userId,Long taskId) {
        Task existingTask = Task.findById(taskId);

        if (existingTask == null) {
            throw new NotFoundException("Task not found");
        }
        if (!existingTask.id.equals(userId)) {
            throw new ForbiddenException("You are not allowed to delete this task");
        }
        existingTask.delete();
    }
}
