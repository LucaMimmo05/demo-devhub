package com.devhub.controllers;

import com.devhub.dto.TaskResponse;
import com.devhub.models.Task;
import com.devhub.services.TaskService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import java.util.List;

@Path("api/task")
public class TaskController {

    TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("access-token")
    public List<TaskResponse> getAllTaskById(@PathParam("id") Long id) {
        return taskService.getTasksByUserId(id);

    }

}
