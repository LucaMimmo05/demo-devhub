package com.devhub.repositories;

import com.devhub.dto.CommandRequest;
import com.devhub.dto.CommandResponse;
import com.devhub.dto.UserResponse;
import com.devhub.models.Command;
import com.devhub.models.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CommandRepository {

    public List<CommandResponse> getCommandByUserId(Long userId) {
        return Command.list("user.id", userId)
                .stream()
                .map(c      -> ((Command) c).toDTO())
                .collect(Collectors.toList());
    }
    @Transactional
    public CommandResponse createCommand(CommandRequest request, Long userId) {
        User user = User.findById(userId);
        if (user == null) {
            throw new WebApplicationException("User not found", 404);
        }

        if (request.getTitle() == null || request.getTitle().isEmpty()) {
            throw new WebApplicationException("Title cannot be empty", 400);
        }

        Command command = new Command();
        command.setTitle(request.getTitle());
        command.setCommandText(request.getCommandText());
        command.setDescription(request.getDescription());
        command.setCreatedAt(LocalDateTime.now());
        command.setUser(user);

        command.persistAndFlush();
        return command.toDTO();
    }


    @Transactional
    public CommandResponse updateCommand(Long commandId, CommandRequest command, UserResponse currentUser) {
        Command existingCommand = Command.findById(commandId);

        if (existingCommand == null) {
            throw new NotFoundException("Command not found");
        }

        if(!existingCommand.user.id.equals(currentUser.getId())) {
            throw new NotFoundException("You are not allowed to update this command");
        }

        existingCommand.setTitle(command.getTitle());
        existingCommand.setCommandText(command.getCommandText());
        existingCommand.setDescription(command.getDescription());

        existingCommand.persist();

        return existingCommand.toDTO();
    }

    @Transactional
    public void deleteCommand(Long userId,Long commandId) {
        Command existingCommand = Command.findById(commandId);

        if (existingCommand == null) {
            throw new NotFoundException("Command not found");
        }

        if(!existingCommand.user.id.equals(userId)) {
            throw new NotFoundException("You are not allowed to delete this command");
        }

        existingCommand.delete();


    }

}
