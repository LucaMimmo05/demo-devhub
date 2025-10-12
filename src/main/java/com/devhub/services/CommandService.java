package com.devhub.services;

import com.devhub.dto.CommandRequest;
import com.devhub.dto.CommandResponse;
import com.devhub.dto.UserResponse;
import com.devhub.exception.BadRequestException;
import com.devhub.exception.NotFoundException;
import com.devhub.exception.UnauthorizedException;
import com.devhub.models.Command;
import com.devhub.models.User;
import com.devhub.repositories.CommandRepository;
import com.devhub.repositories.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CommandService {

    @Inject
    CommandRepository commandRepository;
    @Inject
    UserRepository userRepository;

    public List<CommandResponse> getCommandsByUser(Long userId) {
        return commandRepository.findByUserId(userId)
                .stream()
                .map(Command::toDTO)
                .collect(Collectors.toList());
    }

    public CommandResponse getRandomCommand(Long userId) {
        Command command = commandRepository.findRandomByUserId(userId);
        if (command == null) throw new NotFoundException("No commands found for this user");
        return command.toDTO();
    }

    public CommandResponse createCommand(CommandRequest request, Long userId) {
        if (request.getTitle() == null || request.getTitle().isBlank())
            throw new BadRequestException("Title cannot be empty");

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        return commandRepository.create(request, user).toDTO();
    }

    public CommandResponse updateCommand(Long commandId, CommandRequest request, Long currentUserId) {
        Command existingCommand = commandRepository.findById(commandId);
        if (existingCommand == null)
            throw new NotFoundException("No command found with id: " + commandId);

        if (!existingCommand.getUser().id.equals(currentUserId))
            throw new UnauthorizedException("You are not allowed to update this command");

        Command updated = commandRepository.update(existingCommand, request);
        return updated.toDTO();
    }

    public void deleteCommand(Long userId, Long commandId) {
        Command command = commandRepository.findById(commandId);
        if (command == null)
            throw new NotFoundException("No command found with id: " + commandId);

        if (!command.getUser().id.equals(userId))
            throw new UnauthorizedException("You are not allowed to delete this command");

        commandRepository.delete(command);
    }
}
