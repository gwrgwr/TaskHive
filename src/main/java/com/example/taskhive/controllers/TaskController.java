package com.example.taskhive.controllers;

import com.example.taskhive.annotations.task.*;
import com.example.taskhive.domain.file.FileDB;
import com.example.taskhive.domain.task.CreateTaskRequestDTO;
import com.example.taskhive.domain.task.Task;
import com.example.taskhive.domain.task.TaskStatus;
import com.example.taskhive.domain.task.UpdateTaskRequestDTO;
import com.example.taskhive.services.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@Tag(name = "Task", description = "Task Operations Controller")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(value = "id/{id}")
    @GetTaskAnnotation
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public Task getTaskById(@PathVariable  String id) {
        return taskService.getTaskById(id);
    }

    @GetMapping(value = "name/{name}")
    @GetTaskAnnotation
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public Task getTaskByName(@PathVariable String name) {
        return taskService.getTaskByName(name);
    }

    @GetMapping(value = "attachments/{id}")
    @GetTaskAnnotation
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<FileDB> getAttachments(@PathVariable String id) {
        return taskService.getFiles(id);
    }

    @PostMapping
    @CreateTaskAnnotation
    @PreAuthorize("hasAuthority('SCOPE_STAFF')")
    public Task saveTask(@RequestBody CreateTaskRequestDTO data) {
        return taskService.saveTask(data);
    }

    @PutMapping(value = "status/{id}")
    @ChangeTaskStatusAnnotation
    @PreAuthorize("hasAuthority('SCOPE_STAFF')")
    public void changeTaskStatus(@PathVariable String id, @RequestParam String status) {
        taskService.changeTaskStatus(id, TaskStatus.valueOf(status));
    }

    @PutMapping(value = "update/{id}")
    @UpdateTaskAnnotation
    @PreAuthorize("hasAuthority('SCOPE_STAFF')")
    public Task updateTask(@PathVariable String id, @RequestBody UpdateTaskRequestDTO data) {
        return taskService.updateTask(id, data);
    }

    @DeleteMapping(value = "delete/{id}")
    @DeleteTaskAnnotation
    @PreAuthorize("hasAuthority('SCOPE_STAFF')")
    public void deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
    }
}
