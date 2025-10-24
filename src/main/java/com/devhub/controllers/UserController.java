package com.devhub.controllers;

import com.devhub.dto.UserRequest;
import com.devhub.dto.UserResponse;
import com.devhub.models.User;
import com.devhub.services.UserService;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.time.LocalDateTime;
import java.util.List;

@Path("api/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class UserController {

    @Inject
    Instance<JsonWebToken> jwtInstance;

    @Inject
    UserService userService;

    public Long getCurrentUserId() {
        JsonWebToken jwt = jwtInstance.get();
        return Long.valueOf(jwt.getSubject());
    }

    @GET
    @RolesAllowed("ADMIN")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAllUsers() {
        return User.listAll();
    }


    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public UserResponse updateUser(UserRequest user) {
        Long userId = getCurrentUserId();
        return userService.updateUser(userId, user);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser() {
        Long userId = getCurrentUserId();
        userService.deleteUser(userId);
        return Response.ok().entity("User deleted successfully").build();
    }





}
