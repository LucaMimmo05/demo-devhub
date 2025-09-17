package com.devhub.services;

import com.devhub.dto.CommandRequest;
import com.devhub.dto.CommandResponse;
import com.devhub.dto.UserResponse;
import com.devhub.models.Command;
import com.devhub.repositories.CommandRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class CommandService {

    @Inject
    CommandRepository commandRepository;

    public List<CommandResponse> getCommandsByUserId(Long userId) {
        return commandRepository.getCommandByUserId(userId);
    }

    public CommandResponse getRandomCommand(Long userId) {
        return commandRepository.getRandomCommand(userId);
    }

    public CommandResponse createCommand(CommandRequest command, Long userId) {
        return commandRepository.createCommand(command , userId);
    }

    public CommandResponse updateCommand(Long commandId, CommandRequest command, UserResponse userResponse) {
        return commandRepository.updateCommand(commandId, command, userResponse);
    }
    public void deleteCommand(Long userId, Long commandId) {
        commandRepository.deleteCommand(userId, commandId);
    }
}
