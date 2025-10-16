package com.devhub.repositories;

import com.devhub.dto.NoteRequest;
import com.devhub.models.Note;
import com.devhub.models.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class NoteRepository {


    public List<Note> findByUserId(Long userId) {
        return Note.list("user.id", userId);
    }

    public Note findById(Long noteId) {
        return Note.findById(noteId);
    }
    @Transactional
    public Note create(NoteRequest note, User user) {
        Note newNote = new Note();
        newNote.setTitle(note.getTitle());
        newNote.setContent(note.getContent());
        newNote.setUser(user);
        newNote.setCreatedAt(LocalDateTime.now());
        newNote.setUpdatedAt(LocalDateTime.now());
        newNote.persist();
        return newNote;

    }
    @Transactional
    public Note update(Note note,NoteRequest request) {
        if (request.getTitle() != null) {
            note.setTitle(request.getTitle());
        }
        if (request.getContent() != null) {
            note.setContent(request.getContent());
        }
        note.setUpdatedAt(LocalDateTime.now());

        Note managed = Note.getEntityManager().merge(note);
        Note.getEntityManager().flush();
        return managed;
    }

    @Transactional
    public void delete(Note note) {
        note.delete();
    }
}
