package com.example.taskhive.repositories;

import com.example.taskhive.domain.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, String> {
    Optional<Task> findByTitle(String title);
}
