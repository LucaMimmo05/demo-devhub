package com.devhub.controllers;

import com.devhub.dto.NoteRequest;
import com.devhub.dto.NoteResponse;
import com.devhub.models.Note;
import com.devhub.services.NoteService;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;

@Path("/api/note")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class NoteController {

    @Inject
    NoteService noteService;

    @Inject
    Instance<JsonWebToken> jwtInstance;

    public Long getCurrentUserId() {
        JsonWebToken jwt = jwtInstance.get();
        return Long.valueOf(jwt.getSubject());
    }

    @GET
    public List<NoteResponse> getAllNotes() {
        Long userId = getCurrentUserId();
        return noteService.getAllNotesByUserId(userId);
    }

    @POST
    public NoteResponse createNote(NoteRequest note) {
        Long userId = getCurrentUserId();
        return noteService.createNote(note, userId);
    }

    @PUT
    @Path("/{id}")
    public NoteResponse updateNote(@PathParam("id") Long noteId, NoteRequest note) {
        Long userId = getCurrentUserId();
        return noteService.updateNote(note, noteId, userId);
    }

    @DELETE
    @Path("/{id}")
    public String deleteNote(@PathParam("id") Long noteId) {
        Long userId = getCurrentUserId();
        noteService.deleteNote(userId, noteId);
        return "Note with id " + noteId + " deleted successfully";
    }
}
