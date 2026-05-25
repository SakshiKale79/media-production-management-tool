package com.media.mediaproject.controller;

import com.media.mediaproject.model.Project;


import com.media.mediaproject.model.Task;
import com.media.mediaproject.model.Team;
import com.media.mediaproject.model.User;
import com.media.mediaproject.repository.ProjectRepository;
import com.media.mediaproject.repository.TaskRepository;
import com.media.mediaproject.repository.TeamRepository;
import com.media.mediaproject.repository.UserRepository;
import com.media.mediaproject.service.ProjectService;
import com.media.mediaproject.service.TaskService;
import com.media.mediaproject.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/manager")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectManagerController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TeamService teamService;
    @Autowired
    private UserRepository userRepository;

  

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;


    // ✅ 1. View all projects by manager
    @GetMapping("/projects/{managerId}")
    public ResponseEntity<List<Project>> getProjectsByManager(@PathVariable Long managerId) {
        return ResponseEntity.ok(projectRepository.findByManagerId(managerId));
    }

    // ✅ 2. Create project (optional — can reuse existing admin endpoint)
    @PostMapping("/projects/create")
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        return ResponseEntity.ok(projectService.createProject(project));
    }

    // ✅ 3. Update project
    @PutMapping("/projects/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @RequestBody Project updated) {
        Optional<Project> opt = projectRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        Project proj = opt.get();
        proj.setTitle(updated.getTitle());
        proj.setDescription(updated.getDescription());
        proj.setDeadline(updated.getDeadline());
        proj.setManager(updated.getManager());

        return ResponseEntity.ok(projectRepository.save(proj));
    }

    // ✅ 4. View all tasks
    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    // ✅ 5. Create task
    @PostMapping("/tasks/create")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.createTask(task));
    }

    // ✅ 6. Update task
    @PutMapping("/tasks/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        Optional<Task> opt = taskRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        Task task = opt.get();
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setDeadline(updatedTask.getDeadline());
        task.setStatus(updatedTask.getStatus());

        return ResponseEntity.ok(taskRepository.save(task));
    }

    // ✅ 7. Assign user to task
    @PutMapping("/tasks/{taskId}/assign/{userId}")
    public ResponseEntity<?> assignUser(@PathVariable Long taskId, @PathVariable Long userId) {
        Optional<Task> taskOpt = taskRepository.findById(taskId);
        Optional<User> userOpt = userRepository.findById(userId);

        if (taskOpt.isEmpty() || userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Task or User not found");
        }

        Task task = taskOpt.get();
        task.setAssignedTo(userOpt.get());
        return ResponseEntity.ok(taskRepository.save(task));
    }

    // ✅ 8. Unassign user from task
    @PutMapping("/tasks/{taskId}/unassign")
    public ResponseEntity<?> unassignUser(@PathVariable Long taskId) {
        Optional<Task> taskOpt = taskRepository.findById(taskId);
        if (taskOpt.isEmpty()) return ResponseEntity.notFound().build();

        Task task = taskOpt.get();
        task.setAssignedTo(null);
        return ResponseEntity.ok(taskRepository.save(task));
    }

    // ✅ 9. Change task status
    @PutMapping("/tasks/{taskId}/status")
    public ResponseEntity<?> changeStatus(@PathVariable Long taskId, @RequestParam String status) {
        return ResponseEntity.ok(taskService.updateTaskStatus(taskId, status));
    }

    // ✅ 10. View tasks by project
    @GetMapping("/tasks/project/{projectId}")
    public ResponseEntity<List<Task>> getTasksByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(taskService.getTasksByProjectId(projectId));
    }

    // ✅ 11. View team list
    @GetMapping("/teams")
    public ResponseEntity<List<Team>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    // ✅ 12. Create team
    @PostMapping("/teams/create")
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        return ResponseEntity.ok(teamService.createTeam(team));
    }
}