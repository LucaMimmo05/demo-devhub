package com.devhub.controllers;

import com.devhub.dto.CommandRequest;
import com.devhub.dto.CommandResponse;
import com.devhub.dto.UserResponse;
import com.devhub.services.CommandService;
import com.devhub.services.UserService;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;
import java.util.Map;

@Path("api/command")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommandController {

    private final CommandService commandService;

    public CommandController(CommandService commandService) {
        this.commandService = commandService;
    }

    @Inject
    Instance<JsonWebToken> jwtInstance;

    public Long getCurrentUserId() {
        JsonWebToken jwt = jwtInstance.get();
        return Long.valueOf(jwt.getSubject());
    }


    @GET
    public List<CommandResponse> getAllCommands() {
        Long currentUserId = getCurrentUserId();
        return commandService.getCommandsByUser(currentUserId);
    }

    @POST
    public CommandResponse createCommand(CommandRequest command) {
        Long currentUserId = getCurrentUserId();
        return commandService.createCommand(command, currentUserId);
    }

    @PUT
    @Path("/{id}")
    public CommandResponse updateCommand(@PathParam("id") Long commandId, CommandRequest command) {
        Long userId = getCurrentUserId();
        return commandService.updateCommand(commandId, command, userId);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCommand(@PathParam("id") Long commandId) {
        Long currentUserId = getCurrentUserId();
        commandService.deleteCommand(currentUserId, commandId);

        String message = "Command with ID " + commandId + " has been deleted.";

        return Response.ok(Map.of("message", message)).build();
    }
    @GET
    @Path("/random")
    public CommandResponse getRandomCommand() {
        Long userId = getCurrentUserId();
        return commandService.getRandomCommand(userId);
    }
}
