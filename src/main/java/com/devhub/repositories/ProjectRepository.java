package com.devhub.repositories;

import com.devhub.dto.ProjectRequest;
import com.devhub.dto.ProjectResponse;
import com.devhub.models.Project;
import com.devhub.models.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProjectRepository {

    public List<ProjectResponse> getAllProjectByUserId(Long userId) {
        return Project.list("user.id", userId)
                .stream()
                .map((p) -> ((Project) p).toDTO())
                .collect(Collectors.toList());
    }

    public ProjectResponse getProjectById(Long projectId) {
        Project project = Project.findById(projectId);
        if (project == null) {
            throw new RuntimeException("Project not found");
        }
        return project.toDTO();
    }
    @Transactional
    public ProjectResponse createProject(ProjectRequest project, Long userId) {

        User user = User.findById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Project newProject = new Project();
        newProject.setName(project.getName());
        newProject.setDescription(project.getDescription());
        newProject.setProgress(project.getProgress());
        newProject.setStatus(project.getStatus());
        newProject.setTechnologies(project.getTechnologies());
        newProject.setNotes(project.getNotes());
        newProject.setUser(user);
        newProject.setFolderColor(project.getFolderColor());

        LocalDateTime now = java.time.LocalDateTime.now();
        newProject.setCreatedAt(now);
        newProject.setUpdatedAt(now);

        newProject.persistAndFlush();
        return newProject.toDTO();
    }
}
