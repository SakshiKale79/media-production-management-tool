//package com.media.mediaproject.controller;
//import com.media.mediaproject.model.Project;
//import com.media.mediaproject.model.Team;
//import com.media.mediaproject.model.User;
//import com.media.mediaproject.service.ProjectService;
//import com.media.mediaproject.service.TeamService;
//import com.media.mediaproject.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/admin")
//@CrossOrigin(origins="http://localhost:4200")
//public class AdminController {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private ProjectService projectService;
//
//    @Autowired
//    private TeamService teamService;
//
//    // ─── User Endpoints ───────────────────────────────────────────────
//
//    @GetMapping("/users")
//    public List<User> getAllUsers() {
//        return userService.getAllUsers();
//    }
//
//    @PostMapping("/users")
//    public User createUser(@RequestBody User user) {
//        return userService.createUser(user);
//    }
//
//    @PutMapping("/users/{id}")
//    public User updateUser(@PathVariable Long id, @RequestBody User user) {
//        return userService.updateUser(id, user);
//    }
//
//    @DeleteMapping("/users/{id}")
//    public void deleteUser(@PathVariable Long id) {
//        userService.deleteUser(id);
//    }
//
//    @PutMapping("/users/{id}/role")
//    public User changeUserRole(@PathVariable Long id, @RequestParam String role) {
//        return userService.changeUserRole(id, role.toUpperCase());
//    }

    // ─── Project Endpoints ─────────────────────────────────────────────

//    @GetMapping("/projects")
//    public List<Project> getAllProjects() {
//        return projectService.getAllProjects();
//    }
//
//    @PostMapping("/projects")
//    public Project createProject(@RequestBody Project project) {
//        return projectService.createProject(project);
//    }
//
//    @PutMapping("/projects/{id}")
//    public Project updateProject(@PathVariable Long id, @RequestBody Project project) {
//        return projectService.updateProject(id, project);
//    }
//
//    @PutMapping("/projects/{id}/assign-manager")
//    public Project assignManager(@PathVariable Long id, @RequestParam Long managerId) {
//        User manager = userService.getUserById(managerId);
//        return projectService.assignManager(id, manager);
//    }
//
//    @GetMapping("/projects/{id}/status")
//    public String getProjectStatus(@PathVariable Long id) {
//        Project project = projectService.getProjectById(id);
//        return (project != null) ? "Project Found: " + project.getTitle() : "Project not found";
//    }

    // ─── Team Endpoints ───────────────────────────────────────────────

//    @GetMapping("/teams")
//    public List<Team> getAllTeams() {
//        return teamService.getAllTeams();
//    }
//
//    @PostMapping("/teams")
//    public Team createTeam(@RequestBody Team team) {
//        return teamService.createTeam(team);
//    }
//
//    @PutMapping("/teams/{id}")
//    public Team updateTeam(@PathVariable Long id, @RequestBody Team updatedTeam) {
//        return teamService.updateTeam(id, updatedTeam);
//    }
//}