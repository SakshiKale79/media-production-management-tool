package com.media.mediaproject.service;

import com.media.mediaproject.model.Project;
import com.media.mediaproject.model.Team;
import com.media.mediaproject.model.User;

import java.util.List;

public interface ProjectService {
    List<Project> getAllProjects();
    Project getProjectById(Long id);
    Project createProject(Project project);
    Project updateProject(Long id, Project updatedProject);
    Project assignProjectManager(Long projectId, User manager);
    Project assignTeam(Long projectId, Team team);
}