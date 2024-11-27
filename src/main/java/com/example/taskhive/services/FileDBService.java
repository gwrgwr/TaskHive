package com.example.taskhive.services;

import com.example.taskhive.domain.file.FileDB;
import com.example.taskhive.domain.task.Task;
import com.example.taskhive.exceptions.file.FileNotFoundException;
import com.example.taskhive.repositories.FileDBRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class FileDBService {
    private final FileDBRepository fileDBRepository;
    private final TaskService taskService;
    public FileDBService(FileDBRepository fileDBRepository, TaskService taskService) {
        this.fileDBRepository = fileDBRepository;
        this.taskService = taskService;
    }

    public void store(MultipartFile file, String taskId) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Task task = taskService.getTaskById(taskId);
        FileDB fileDB = new FileDB(fileName, file.getContentType(), file.getBytes(), task);
        System.out.println(Arrays.toString(file.getBytes()));
        fileDBRepository.save(fileDB);
    }

    public FileDB getFile(String id) {
        return fileDBRepository.findById(id).orElseThrow(FileNotFoundException::new);
    }

    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }
}
