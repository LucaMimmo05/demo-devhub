package com.devhub.controllers;

import com.devhub.models.User;
import com.devhub.repositories.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Path("api/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {



    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAllUsers() {
        return User.listAll();
    }

    @POST
    @Transactional
    public Response createUser(User user) {
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(LocalDateTime.now());
        }

        user.persist();

        // Ritorna una risposta con l'utente creato e lo status 201 (Created)
        return Response.status(Response.Status.CREATED)
                .entity(user)
                .build();
    }

}
