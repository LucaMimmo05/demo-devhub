package com.devhub.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class UnauthroziedExceptionMapper implements ExceptionMapper<UnauthorizedException> {
    @Override
    public Response toResponse(UnauthorizedException e) {
        ApiError error = new ApiError(
                Response.Status.UNAUTHORIZED.getStatusCode(),
                "Unauthorized",
                e.getMessage()
        );

        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(error)
                .build();
    }
}
