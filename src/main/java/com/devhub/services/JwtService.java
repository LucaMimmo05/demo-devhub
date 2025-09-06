package com.devhub.services;

import com.devhub.dto.LoginResponse;
import com.devhub.dto.UserResponse;
import com.devhub.models.role.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.WebApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;

// ...
@ApplicationScoped
public class JwtService {
    private static final Logger LOG = LoggerFactory.getLogger(JwtService.class);

    private final PublicKey publicKey;

    @Inject
    public JwtService(PublicKey publicKey) {
        this.publicKey = publicKey;
    }


    public LoginResponse verifyToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new WebApplicationException("Token is required", 400);
        }

        UserResponse userResponse = validateAccessToken(token);
        if (userResponse == null) {
            throw new WebApplicationException("Invalid or expired token", 401);
        }

        if (!isTokenValid(token)) {
            throw new WebApplicationException("Token is not valid", 401);
        }

        return new LoginResponse("Token is valid", token, null);
    }

    public String generateAccessToken(UserResponse user) {
        return Jwt
                .issuer("demo-devhub")
                .subject(String.valueOf(user.getId()))
                .upn(user.getEmail())
                .groups(Set.of(user.getRole().name(), "access-token"))
                .claim("nickname", user.getEmail())
                .claim("email", user.getEmail())
                .claim("name", user.getName())
                .claim("surname", user.getSurname())
                .expiresIn(Duration.ofMinutes(15))
                .issuedAt(Instant.now())
                .sign();
    }

    public String generateRefreshToken(UserResponse user) {

        return Jwt
                .issuer("demo-devhub")
                .subject(String.valueOf(user.getId()))
                .upn(user.getEmail())
                .groups(Set.of(user.getRole().name(), "refresh_token"))
                .claim("nickname", user.getEmail())
                .claim("email", user.getEmail())
                .claim("name", user.getName())
                .claim("surname", user.getSurname())
                .expiresIn(Duration.ofDays(7))
                .issuedAt(Instant.now())
                .sign();
    }


    public UserResponse validateAccessToken(String token) {
        try {
            Jws<Claims> jwsClaims = Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(token);

            Claims claims = jwsClaims.getBody();

            @SuppressWarnings("unchecked")
            List<String> groups = (List<String>) claims.get("groups");
            if (groups == null || !groups.contains("access-token")) {
                LOG.warn("Token missing access-token group");
                return null;
            }

            if (claims.getExpiration() == null || claims.getExpiration().before(new java.util.Date())) {
                LOG.warn("Token expired");
                return null;
            }

            String subject = claims.getSubject();
            String email = claims.get("email", String.class);
            String name = claims.get("name", String.class);
            String surname = claims.get("surname", String.class);

            String roleStr = groups.stream()
                    .filter(g -> !g.equals("access-token"))
                    .findFirst()
                    .orElse("USER");

            Role role = Role.valueOf(roleStr);

            return new UserResponse(Long.valueOf(subject), name, surname, email, role);

        } catch (Exception e) {
            LOG.warn("Access token validation failed: {}", e.getMessage());
            return null;
        }
    }
    public boolean isTokenValid(String token) {
        try {
            Jws<Claims> jwsClaims = Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(token);

            Claims claims = jwsClaims.getBody();

            // Controlla se il token è scaduto
            if (claims.getExpiration() == null || claims.getExpiration().before(new java.util.Date())) {
                LOG.warn("Token expired");
                return false;
            }

            // Se vuoi, puoi anche controllare altri claim o gruppi qui

            return true;
        } catch (Exception e) {
            LOG.warn("Token validation error: {}", e.getMessage());
            return false;
        }
    }

    public UserResponse validateRefreshToken(String token) {
        try {
            Jws<Claims> jwsClaims = Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(token);

            Claims claims = jwsClaims.getBody();

            @SuppressWarnings("unchecked")
            List<String> groups = (List<String>) claims.get("groups");
            if (groups == null || !groups.contains("refresh_token")) {
                LOG.warn("Token missing refresh_token group");
                return null;
            }

            if (claims.getExpiration() == null || claims.getExpiration().before(new java.util.Date())) {
                LOG.warn("Refresh token expired");
                return null;
            }

            String subject = claims.getSubject();
            String email = claims.get("email", String.class);
            String name = claims.get("name", String.class);
            String surname = claims.get("surname", String.class);

            String roleStr = groups.stream()
                    .filter(g -> !g.equals("refresh_token"))
                    .findFirst()
                    .orElse("USER");

            Role role = Role.valueOf(roleStr);

            return new UserResponse(Long.valueOf(subject), name, surname, email, role);

        } catch (Exception e) {
            LOG.warn("Refresh token validation failed: {}", e.getMessage());
            return null;
        }
    }


}
