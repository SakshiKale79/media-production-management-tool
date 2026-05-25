package com.media.mediaproject.service;

import com.media.mediaproject.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> getAllTasks();
    List<Task> getTasksByProjectId(Long projectId);
    Task createTask(Task task);
    Task updateTaskStatus(Long taskId, String status);
    Optional<Task> getTaskById(Long id);
    Task updateTaskDetails(Long id, Task updatedTask);
    void deleteTask(Long id);
}