package com.devhub.controllers;

import com.devhub.models.Project;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;


@Path("api/project")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProjectController {

    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public List<Project> getAllProjects() {
        return Project.listAll();
    }
}
