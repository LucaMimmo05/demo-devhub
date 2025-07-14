package com.devhub.controllers;

import com.devhub.models.User;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;

@Path("api/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {



    @GET
    @Transactional
    @RolesAllowed("ADMIN")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAllUsers() {
        return User.listAll();
    }




}
