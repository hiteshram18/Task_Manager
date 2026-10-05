package com.hitesh.Task_Manager.service;

import com.hitesh.Task_Manager.dto.TaskDTO;
import com.hitesh.Task_Manager.entity.Task;
import com.hitesh.Task_Manager.entity.TaskStatus;
import com.hitesh.Task_Manager.entity.User;
import com.hitesh.Task_Manager.repository.TaskRepository;
import com.hitesh.Task_Manager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository,
                       UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public TaskDTO createTask(TaskDTO taskDTO, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = new Task();

        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());

        if (taskDTO.getStatus() != null) {
            task.setStatus(taskDTO.getStatus());
        } else {
            task.setStatus(TaskStatus.TODO);
        }

        task.setDueDate(taskDTO.getDueDate());
        task.setUser(user);

        Task savedTask = taskRepository.save(task);

        return convertToDTO(savedTask);
    }

    public List<TaskDTO> getAllTasks(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return taskRepository.findByUser(user)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public TaskDTO getTaskById(Long id, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = taskRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        return convertToDTO(task);
    }

    public TaskDTO updateTask(Long id, TaskDTO taskDTO, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = taskRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());
        task.setDueDate(taskDTO.getDueDate());

        Task updatedTask = taskRepository.save(task);

        return convertToDTO(updatedTask);
    }

    public void deleteTask(Long id, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = taskRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        taskRepository.delete(task);
    }

    private TaskDTO convertToDTO(Task task) {

        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getDueDate()
        );
    }
}