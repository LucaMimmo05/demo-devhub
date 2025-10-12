package com.devhub.repositories;

import com.devhub.dto.ProjectRequest;
import com.devhub.models.Project;
import com.devhub.models.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class ProjectRepository {


    public List<Project> findByUserId(Long userId) {
        return Project.list("user.id", userId);
    }

    public Project getProjectById(Long projectId) {
        return Project.findById(projectId);
    }
    @Transactional
    public Project create(ProjectRequest project, User user) {
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
        return newProject;
    }
    @Transactional
    public Project update(Project project, ProjectRequest request, Long userId) {
        if (request.getName() != null) {
            project.setName(request.getName());
        }
        if (request.getStatus() != null) {
            project.setStatus(request.getStatus());
        }
        if (request.getFolderColor() != null) {
            project.setFolderColor(request.getFolderColor());
        }
        Project managed = Project.getEntityManager().merge(project);
        Project.getEntityManager().flush();

        return managed;
    }

    @Transactional
    public void delete(Project project) {
        project.delete();
    }
}
