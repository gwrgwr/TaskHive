package com.example.taskhive.repositories;

import com.example.taskhive.domain.file.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDBRepository extends JpaRepository<FileDB, String> {
}
