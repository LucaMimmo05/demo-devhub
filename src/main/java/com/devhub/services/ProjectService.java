package com.devhub.services;

import com.devhub.dto.ProjectRequest;
import com.devhub.dto.ProjectResponse;
import com.devhub.exception.BadRequestException;
import com.devhub.exception.NotFoundException;
import com.devhub.models.Project;
import com.devhub.models.User;
import com.devhub.repositories.ProjectRepository;
import com.devhub.repositories.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ForbiddenException;


import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProjectService {

    @Inject
    ProjectRepository projectRepository;
    @Inject
    UserRepository userRepository;

    public List<ProjectResponse> getAllProjectByUserId(Long userId) {
        return projectRepository.findByUserId(userId)
                .stream()
                .map((Project::toDTO))
                .collect(Collectors.toList());
    }

    public ProjectResponse getProjectById(Long projectId) {
        Project project = projectRepository.getProjectById(projectId);
        if (project == null) {
            throw new NotFoundException("Project not found");
        }
        return project.toDTO() ;
    }

    public ProjectResponse createProject(ProjectRequest project, Long userId) {
        if (project.getName() == null || project.getName().isEmpty()) {
            throw new BadRequestException("Project name is empty");
        }

        if (project.getProgress() < 0 || project.getProgress() > 100) {
            throw new BadRequestException("Project progress must be between 0 and 100");
        }
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        return projectRepository.create(project, user).toDTO();
    }

    public ProjectResponse updateProject(Long projectId, ProjectRequest request, Long userId) {
        Project existingProject = Project.findById(projectId);
        if (existingProject == null) {
            throw new NotFoundException("Project not found");
        }
        if (!existingProject.getUser().id.equals(userId)) {
            throw new ForbiddenException("Forbidden");
        }
        Project updated = projectRepository.update(existingProject,request);
        return updated.toDTO();
    }

    public void deleteProject(Long userId, Long projectId) {
        Project project = projectRepository.getProjectById(projectId);
        if (project == null) {
            throw new NotFoundException("Project not found");
        }
        if (!project.getUser().id.equals(userId)) {
            throw new ForbiddenException("Forbidden");
        }
        projectRepository.delete(project);
    }

}
