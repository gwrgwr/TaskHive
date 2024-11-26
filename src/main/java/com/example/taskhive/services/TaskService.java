package com.example.taskhive.services;

import com.example.taskhive.domain.file.FileDB;
import com.example.taskhive.domain.list.ListEntity;
import com.example.taskhive.domain.task.CreateTaskRequestDTO;
import com.example.taskhive.domain.task.Task;
import com.example.taskhive.domain.task.TaskStatus;
import com.example.taskhive.domain.task.UpdateTaskRequestDTO;
import com.example.taskhive.domain.user.UserEntity;
import com.example.taskhive.exceptions.task.TaskNotFoundException;
import com.example.taskhive.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ListEntityService listEntityService;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository, ListEntityService listEntityService, UserService userService) {
        this.taskRepository = taskRepository;
        this.listEntityService = listEntityService;
        this.userService = userService;
    }

    public Task getTaskById(String id) {
        return taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
    }

    public Task getTaskByName(String title) {
        return taskRepository.findByTitle(title).orElseThrow(TaskNotFoundException::new);
    }

    public List<FileDB> getFiles(String id) {
        return getTaskById(id).getFiles();
    }

    public Task saveTask(CreateTaskRequestDTO data) {
        ListEntity listEntity = listEntityService.getListById(data.listId());
        UserEntity userEntity = userService.getUserById(data.assigneeId());
        Task task = new Task(data.title(), data.description(), userEntity, listEntity, data.dueDate());
        return taskRepository.save(task);
    }

    public void changeTaskStatus(String id, TaskStatus status) {
        Task task = getTaskById(id);
        task.setStatus(status);
        taskRepository.save(task);
    }

    public Task updateTask(String id, UpdateTaskRequestDTO data) {
        Task task = getTaskById(id);
        task.setTitle(data.title());
        task.setDescription(data.description());
        task.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public void deleteTask(String id) {
        taskRepository.deleteById(getTaskById(id).getId());
    }
}
