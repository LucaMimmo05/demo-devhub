package com.devhub.services;

import com.devhub.dto.NoteRequest;
import com.devhub.dto.NoteResponse;
import com.devhub.exception.BadRequestException;
import com.devhub.exception.NotFoundException;
import com.devhub.models.Note;
import com.devhub.models.User;
import com.devhub.repositories.NoteRepository;
import com.devhub.repositories.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class NoteService {

    @Inject
    NoteRepository noteRepository;

    @Inject
    UserRepository userRepository;

    public List<NoteResponse> getAllNotesByUserId(Long userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        return noteRepository.findByUserId(userId).stream()
                .map((Note::toDTO))
                .collect(Collectors.toList());
    }

    public NoteResponse createNote(NoteRequest note, Long userId) {
        if (note.getTitle() == null || note.getTitle().isEmpty()) {
            throw new BadRequestException("Note title is empty");
        }
        User user = userRepository.findUserById(userId);
        return noteRepository.create(note, user).toDTO();
    }

    public NoteResponse updateNote(NoteRequest note, Long noteId, Long userId) {
        if (note == null) {
            throw new BadRequestException("Note data is null");
        }
        Note existingNote = noteRepository.findById(noteId);
        if (existingNote == null || !existingNote.getUser().id.equals(userId)) {
            throw new NotFoundException("Note not found or access denied");
        }
        Note updatedNote = noteRepository.update(existingNote, note);
        return updatedNote.toDTO();
    }
    public void deleteNote(Long userId, Long noteId) {
        Note existingNote = noteRepository.findById(noteId);
        if (existingNote == null || !existingNote.getUser().id.equals(userId)) {
            throw new NotFoundException("Note not found or access denied");
        }
        noteRepository.delete(existingNote);
    }
}
