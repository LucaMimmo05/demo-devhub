package com.devhub.controllers;

import jakarta.inject.Inject;


import com.devhub.dto.LoginRequest;
import com.devhub.dto.LoginResponse;
import com.devhub.models.User;
import com.devhub.services.JwtService;
import com.devhub.dto.UserResponse;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import com.devhub.dto.RegisterRequest;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.mindrot.jbcrypt.BCrypt;

@Path("/auth")
public class AuthController {
    @Inject
    JwtService jwtService;

    @POST
    @Path("/login")
    @Transactional
    public Response login(LoginRequest request) {
        User user = User.findByEmail(request.email);

        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new LoginResponse("User not found")).build();
        }

        if (!BCrypt.checkpw(request.password, user.password)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new LoginResponse("Invalid password")).build();
        }

        UserResponse userResponse = new UserResponse(
                user.id,
                user.email,
                user.role,
                user.name,
                user.surname
        );

        String accessToken = jwtService.generateAccessToken(userResponse);
        String refreshToken = jwtService.generateRefreshToken(userResponse);


        return Response.ok(new LoginResponse("Login successful", accessToken,refreshToken)).build();
    }
    @POST
    @Path("/register")
    @Transactional
    public Response register(RegisterRequest request) {
        User existingUser = User.findByEmail(request.email);
        if (existingUser != null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new LoginResponse("User already exists"))
                    .build();
        }

        String hashedPassword = BCrypt.hashpw(request.password, BCrypt.gensalt());

        User newUser = new User();
        newUser.email = request.email;
        newUser.password = hashedPassword;
        newUser.name = request.name;
        newUser.surname = request.surname;
        newUser.role = request.role != null ? request.role : "user";

        newUser.persist();

        UserResponse userResponse = new UserResponse(
                newUser.id,
                newUser.email,
                newUser.role,
                newUser.name,
                newUser.surname
        );

        String accessToken = jwtService.generateAccessToken(userResponse);
        String refreshToken = jwtService.generateRefreshToken(userResponse);

        return Response.ok(new LoginResponse("Registration successful", accessToken, refreshToken)).build();
    }
}
