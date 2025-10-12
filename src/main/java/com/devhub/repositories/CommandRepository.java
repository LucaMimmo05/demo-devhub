package com.devhub.repositories;

import com.devhub.dto.CommandRequest;
import com.devhub.models.Command;
import com.devhub.models.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
@ApplicationScoped
public class CommandRepository {

    public List<Command> findByUserId(Long userId) {
        return Command.list("user.id", userId);
    }

    public Command findRandomByUserId(Long userId) {
        return Command.find("user.id = ?1 ORDER BY RANDOM()", userId).firstResult();
    }

    public Command findById(Long commandId) {
        return Command.findById(commandId);
    }

    @Transactional
    public Command create(CommandRequest request, User user) {
        Command command = new Command();
        command.setTitle(request.getTitle());
        command.setCommandText(request.getCommandText());
        command.setDescription(request.getDescription());
        command.setCreatedAt(LocalDateTime.now());
        command.setUser(user);

        command.persistAndFlush();
        return command;
    }

    @Transactional
    public Command update(Command command, CommandRequest request) {
        if (request.getTitle() != null) {
            command.setTitle(request.getTitle());
        }
        if (request.getCommandText() != null) {
            command.setCommandText(request.getCommandText());
        }
        if (request.getDescription() != null) {
            command.setDescription(request.getDescription());
        }
        Command managed = Command.getEntityManager().merge(command);
        Command.getEntityManager().flush();

        return managed;
    }


    @Transactional
    public void delete(Command command) {
        command.delete();
    }
}
