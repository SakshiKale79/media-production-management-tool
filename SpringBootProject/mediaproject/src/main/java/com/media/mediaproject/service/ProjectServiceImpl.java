package com.media.mediaproject.service;

import com.media.mediaproject.model.Project;
import com.media.mediaproject.model.Team;
import com.media.mediaproject.model.User;
import com.media.mediaproject.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    @Override
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Long id, Project updatedProject) {
        Optional<Project> existingOpt = projectRepository.findById(id);
        if (existingOpt.isPresent()) {
            Project project = existingOpt.get();
            project.setTitle(updatedProject.getTitle());
            project.setDescription(updatedProject.getDescription());
            project.setStatus(updatedProject.getStatus());
            return projectRepository.save(project);
        }
        return null;
    }

    @Override
    public Project assignProjectManager(Long projectId, User manager) {
        Optional<Project> existingOpt = projectRepository.findById(projectId);
        if (existingOpt.isPresent()) {
            Project project = existingOpt.get();
            project.setManager(manager);
            return projectRepository.save(project);
        }
        return null;
    }

    @Override
    public Project assignTeam(Long projectId, Team team) {
        Optional<Project> existingOpt = projectRepository.findById(projectId);
        if (existingOpt.isPresent()) {
            Project project = existingOpt.get();
            project.setTeam(team);
            return projectRepository.save(project);
        }
        return null;
    }
}