package com.devhub.controllers;

import com.devhub.dto.ProjectRequest;
import com.devhub.dto.ProjectResponse;
import com.devhub.models.Project;
import com.devhub.services.ProjectService;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;


@Path("api/project")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class ProjectController {

    @Inject
    ProjectService projectService;

    @Inject
    Instance<JsonWebToken> jwtInstance;

    public Long getCurrentUserId() {
        JsonWebToken jwt = jwtInstance.get(); // prende il bean disponibile
        return Long.valueOf(jwt.getSubject());
    }

    @GET
    public List<ProjectResponse> getProjectsByUserId() {
        Long userId = getCurrentUserId();
        return projectService.getAllProjectByUserId(userId) ;
    }

    @POST
    public ProjectResponse createProject(ProjectRequest project) {
        Long userId = getCurrentUserId();
        return projectService.createProject(project, userId);
    }
}
