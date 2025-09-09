package com.devhub.controllers;

import com.devhub.dto.*;
import com.devhub.models.role.Role;
import com.devhub.services.UserService;


import com.devhub.models.User;
import com.devhub.services.JwtService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("api/auth")
public class AuthController {
    JwtService jwtService;
    UserService userService;

    public AuthController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @POST
    @Path("/login")
    @Transactional
    public Response login(LoginRequest request) {
        LoginResponse response = userService.authenticateAndGenerateToken(request);

        return Response.ok(response).build();
    }
    @POST
    @Path("/register")
    public Response register(RegisterRequest request) {
        if (userService.checkExistingUser(request)) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new LoginResponse("User already exists"))
                    .build();
        }

        LoginResponse response = userService.registerAndGenerateToken(request);

        return Response.ok(response).build();
    }
    @POST
    @Path("/refresh")
    public Response refreshToken(RefreshTokenRequest request) {
        LoginResponse response = userService.refreshTokens(request.getRefreshToken());
        return Response.ok(response).build();
    }

    @POST
    @Path("/verify")
    public Response verifyToken(TokenRequest request) {
        String token = request.getToken();
        if (token == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Token is required")
                    .build();
        }

        try {
            LoginResponse response = jwtService.verifyToken(token, "access-token");
            return Response.ok(response).build();
        } catch (WebApplicationException e) {
            return Response.status(e.getResponse().getStatus())
                    .entity(e.getMessage())
                    .build();
        }
    }






}
