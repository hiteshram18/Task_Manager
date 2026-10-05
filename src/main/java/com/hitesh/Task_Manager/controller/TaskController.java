package com.hitesh.Task_Manager.controller;

import com.hitesh.Task_Manager.dto.TaskDTO;
import com.hitesh.Task_Manager.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Create Task
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(
            @RequestBody TaskDTO taskDTO,
            Authentication authentication) {

        String email = authentication.getName();

        TaskDTO createdTask = taskService.createTask(taskDTO, email);

        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    // Get All Tasks
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks(
            Authentication authentication) {

        String email = authentication.getName();

        List<TaskDTO> tasks = taskService.getAllTasks(email);

        return ResponseEntity.ok(tasks);
    }

    // Get Task By ID
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        TaskDTO task = taskService.getTaskById(id, email);

        return ResponseEntity.ok(task);
    }

    // Update Task
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(
            @PathVariable Long id,
            @RequestBody TaskDTO taskDTO,
            Authentication authentication) {

        String email = authentication.getName();

        TaskDTO updatedTask =
                taskService.updateTask(id, taskDTO, email);

        return ResponseEntity.ok(updatedTask);
    }

    // Delete Task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        taskService.deleteTask(id, email);

        return ResponseEntity.noContent().build();
    }
}