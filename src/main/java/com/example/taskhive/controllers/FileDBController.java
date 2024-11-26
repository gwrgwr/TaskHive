package com.example.taskhive.controllers;

import com.example.taskhive.annotations.file.GetFileAnnotation;
import com.example.taskhive.annotations.file.UploadFileAnnotation;
import com.example.taskhive.domain.file.FileDB;
import com.example.taskhive.domain.file.ResponseFileDTO;
import com.example.taskhive.services.FileDBService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/file")
@Tag(name = "File", description = "File operations")
public class FileDBController {
    private final FileDBService fileDBService;

    public FileDBController(FileDBService fileDBService) {
        this.fileDBService = fileDBService;
    }
    @PostMapping("/upload/")
    @UploadFileAnnotation
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<ResponseFileDTO> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            fileDBService.store(file);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseFileDTO(file.getOriginalFilename(), file.getContentType(), file.getContentType(), file.getSize()));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @GetMapping("/files")
    @GetFileAnnotation
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<List<ResponseFileDTO>> getFiles() {
        List<ResponseFileDTO> files = fileDBService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("api/v1/file/files/")
                    .path(dbFile.getId())
                    .toUriString();
            return new ResponseFileDTO(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/files/{id}")
    @GetFileAnnotation
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        FileDB fileDB = fileDBService.getFile(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(fileDB.getType()));
        headers.setContentDisposition(ContentDisposition.inline().filename(fileDB.getName()).build());
        return ResponseEntity.ok()
                .headers(headers)
                .body(fileDB.getData());
    }
}
