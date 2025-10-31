package com.devhub.repositories;

import com.devhub.dto.TaskRequest;
import com.devhub.dto.TaskResponse;
import com.devhub.models.Task;
import com.devhub.models.User;
import com.devhub.models.task.TaskPriority;
import com.devhub.models.task.Status;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class TaskRepository {

    public List<Task> getTasksByUserId(Long userId) {
        return Task.list("user.id", userId);
    }
    public Task findById(Long id) {
        return Task.findById(id);
    }
    public List<Task> getNotCompletedTasksByUserId(Long userId) {
        return Task.list("user.id = ?1 and status != ?2", userId, Status.DONE);
    }

    public List<Task> getAllCompletedTasksByUserId(Long userId) {
        return Task.list("user.id = ?1 and status = ?2", userId, Status.DONE);
    }
    @Transactional
    public Task create(TaskRequest request, User user) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(Status.valueOf(request.getStatus().name()));
        task.setPriority(TaskPriority.valueOf(request.getPriority().name()));
        task.setDueDate(request.getDueDate());
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        task.setUser(user);

        task.persistAndFlush();
        return task;
    }

    @Transactional
    public Task update(Task task, TaskRequest request) {
        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }
        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }

        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }
        if (request.getPriority() != null) {
            task.setPriority(request.getPriority());
        }
        if (request.getDueDate() != null) {
            task.setDueDate(request.getDueDate());
        }

        Task managed = Task.getEntityManager().merge(task);
        Task.getEntityManager().flush();

        return managed;
    }


    @Transactional
    public void delete(Task task) {
        task.delete();
    }

    @Transactional
    public TaskResponse complete(Task task) {
        task.setStatus(Status.DONE);
        task.setCompletedAt(LocalDateTime.now());

        task.setUpdatedAt(LocalDateTime.now());
        Task managed = Task.getEntityManager().merge(task);
        Task.getEntityManager().flush();
        return managed.toDTO();
    }
    @Transactional
    public void deleteOldCompletedTasks() {
        Task.delete("status = ?1 AND completedAt <= ?2", Status.DONE, LocalDateTime.now().minusDays(30));
    }

}
