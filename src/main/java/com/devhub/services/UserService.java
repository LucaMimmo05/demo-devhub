package com.devhub.services;

import com.devhub.dto.LoginRequest;
import com.devhub.dto.LoginResponse;
import com.devhub.dto.RegisterRequest;
import com.devhub.dto.UserResponse;
import com.devhub.models.User;
import com.devhub.models.role.Role;
import com.devhub.repositories.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import org.mindrot.jbcrypt.BCrypt;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    JwtService jwtService;




    public String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    public LoginResponse authenticateAndGenerateToken(LoginRequest request) {
        User user = findUser(request.getEmail());
        if (user == null || !checkPassword(request.getPassword(), user.getPassword())) {
            throw new WebApplicationException("Invalid Credentials", 401);
        }
        UserResponse userResponse = createResponse(user);
        String accessToken = jwtService.generateAccessToken(userResponse);
        String refreshToken = jwtService.generateRefreshToken(userResponse);
        return new LoginResponse("Login successful", accessToken, refreshToken) ;
    }

    public User findUser(String email) {
        return userRepository.findUserByEmail(email);
    }

    public boolean checkExistingUser(RegisterRequest request) {
        User user = userRepository.checkExistingUser(request);
        return user != null;
    }

    public LoginResponse registerAndGenerateToken(RegisterRequest request) {
        String hashedPassword = hashPassword(request.getPassword());

        User newUser = createUser(request, hashedPassword);

        userRepository.createUser(newUser);
        UserResponse userResponse = createResponse(newUser);

        String accessToken = jwtService.generateAccessToken(userResponse);
        String refreshToken = jwtService.generateRefreshToken(userResponse);
        return new LoginResponse("Registration successful", accessToken, refreshToken);
    }

    private User createUser(RegisterRequest request, String hashedPassword) {
        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setName(request.getName());
        newUser.setSurname(request.getSurname());
        newUser.setPassword(hashedPassword);
        String roleString = request.getRole() != null ? request.getRole() : "USER";
        Role role;
        try {
            role = Role.valueOf(roleString);
        }catch (IllegalArgumentException e){
            role = Role.USER;
        }
        newUser.setRole(role);

        return newUser;
    }

    private UserResponse createResponse(User user) {
        return new UserResponse(
                user.id,
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getRole()
        );
    }

    public LoginResponse refreshTokens(String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new WebApplicationException("Refresh token is required", 400);
        }

        UserResponse userResponse = jwtService.validateRefreshToken(refreshToken);
        if (userResponse == null) {
            throw new WebApplicationException("Invalid or expired refresh token", 401);
        }

        String newAccessToken = jwtService.generateAccessToken(userResponse);
        String newRefreshToken = jwtService.generateRefreshToken(userResponse);

        return new LoginResponse("Token refreshed", newAccessToken, newRefreshToken);
    }

}
