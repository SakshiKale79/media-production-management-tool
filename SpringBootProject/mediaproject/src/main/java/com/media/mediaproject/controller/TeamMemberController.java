package com.media.mediaproject.controller;

import com.media.mediaproject.model.Task;
import com.media.mediaproject.repository.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/team-member")
@CrossOrigin(origins = "http://localhost:4200") // Allow frontend access
public class TeamMemberController {

    @Autowired
    private TaskRepository taskRepository;

    // 🔹 Get tasks assigned to the team member
    @GetMapping("/tasks/{userId}")
    public ResponseEntity<List<Task>> getTasksByUser(@PathVariable Long userId) {
        List<Task> tasks = taskRepository.findByAssignedToId(userId);
        return ResponseEntity.ok(tasks);
    }

    // 🔹 Update task status (e.g., from PENDING → IN_PROGRESS → COMPLETED)
    @PutMapping("/tasks/{taskId}/status")
    public ResponseEntity<String> updateTaskStatus(
            @PathVariable Long taskId,
            @RequestParam String status) {

        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setStatus(status);
            taskRepository.save(task);
            return ResponseEntity.ok("Status updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }
    }
}