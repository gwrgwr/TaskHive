package com.example.taskhive.services;

import com.example.taskhive.domain.file.FileDB;
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

    public FileDBService(FileDBRepository fileDBRepository) {
        this.fileDBRepository = fileDBRepository;
    }

    public void store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        FileDB fileDB = new FileDB(fileName, file.getContentType(), file.getBytes());
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
