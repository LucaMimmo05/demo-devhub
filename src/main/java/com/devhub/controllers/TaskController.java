package com.devhub.controllers;

import com.devhub.dto.TaskRequest;
import com.devhub.dto.TaskResponse;
import com.devhub.dto.UserResponse;
import com.devhub.services.TaskService;
import com.devhub.services.UserService;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import net.bytebuddy.asm.MemberSubstitution;
import org.eclipse.microprofile.jwt.JsonWebToken;


import java.util.List;
import java.util.Map;

@Path("api/task")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @Inject
    Instance<JsonWebToken> jwtInstance;

    public Long getCurrentUserId() {
        JsonWebToken jwt = jwtInstance.get(); // prende il bean disponibile
        return Long.valueOf(jwt.getSubject());
    }




    // Recupera tutte le task dell'utente autenticato
    @GET
    public List<TaskResponse> getAllTasks() {
        Long currentUserId = getCurrentUserId();
        return taskService.getTasksByUserId(currentUserId);
    }

    // Crea una task per l'utente autenticato
    @POST
    public TaskResponse createTask(TaskRequest request) {
        Long currentUserId = getCurrentUserId();
        return taskService.createTask(request, currentUserId);
    }

    // Aggiorna una task dell'utente autenticato
    @PUT
    @Path("/{id}")
    public TaskResponse updateTask(@PathParam("id") Long taskId,
                                   TaskRequest task) {
        // Ottieni l'ID dell'utente dal JWT
        Long currentUserId = getCurrentUserId();
        // Se vuoi più info, puoi recuperare dal DB o dal service
        UserResponse currentUser = userService.getUserById(currentUserId);

        return taskService.updateTask(taskId, task, currentUser);
    }

    // Elimina una task dell'utente autenticato
    @DELETE
    @Path("/{id}")
    public Response deleteTask(
                               @PathParam("id") Long taskId) {
        Long currentUserId = getCurrentUserId();
        taskService.deleteTask(currentUserId, taskId);

        String message = String.format("Task with id %d has been successfully deleted.", taskId);
        return Response.ok(Map.of("message", message)).build();
    }
}
