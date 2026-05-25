package com.media.mediaproject.controller;

import com.media.mediaproject.model.Project;
import com.media.mediaproject.model.User;
import com.media.mediaproject.repository.ProjectRepository;
import com.media.mediaproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Get all projects
    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectRepository.findAll());
    }

    // ✅ Get project by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getProjectById(@PathVariable Long id) {
        Optional<Project> project = projectRepository.findById(id);
        return project.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Create new project - Angular calls POST /projects
    @PostMapping("/create")
    public ResponseEntity<?> createProject(@RequestBody Project project) {
        projectRepository.save(project);
        return ResponseEntity.ok("Project created successfully");
    }

    // ✅ Update existing project - Angular calls PUT /projects/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @RequestBody Project updatedProject) {
        Optional<Project> projectOpt = projectRepository.findById(id);
        if (projectOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Project project = projectOpt.get();
        project.setTitle(updatedProject.getTitle());
        project.setDescription(updatedProject.getDescription());
        project.setStatus(updatedProject.getStatus());
        projectRepository.save(project);

        return ResponseEntity.ok("Project updated successfully");
    }

    // ✅ Assign project manager
    @PutMapping("/{projectId}/assign-manager/{managerId}")
    public ResponseEntity<?> assignManager(@PathVariable Long projectId, @PathVariable Long managerId) {
        Optional<Project> projectOpt = projectRepository.findById(projectId);
        Optional<User> managerOpt = userRepository.findById(managerId);

        if (projectOpt.isEmpty() || managerOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid project or manager ID");
        }

        Project project = projectOpt.get();
        User manager = managerOpt.get();
        project.setManager(manager);
        projectRepository.save(project);

        return ResponseEntity.ok("Manager assigned successfully");
    }
}