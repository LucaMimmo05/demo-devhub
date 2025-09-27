package com.devhub.services;

import com.devhub.dto.ProjectRequest;
import com.devhub.dto.ProjectResponse;
import com.devhub.models.Project;
import com.devhub.repositories.ProjectRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class ProjectService {

    @Inject
    ProjectRepository projectRepository;

    public List<ProjectResponse> getAllProjectByUserId(Long userId) {
        return projectRepository.getAllProjectByUserId(userId);
    }

    public ProjectResponse getProjectById(Long projectId) {
        return projectRepository.getProjectById(projectId);
    }

    public ProjectResponse createProject(ProjectRequest project, Long userId) {
        return projectRepository.createProject(project, userId);
    }

    public ProjectResponse updateProject(Long projectId, ProjectRequest project, Long currentUserId) {
        return projectRepository.updateProject(projectId, project, currentUserId);
    }
}
