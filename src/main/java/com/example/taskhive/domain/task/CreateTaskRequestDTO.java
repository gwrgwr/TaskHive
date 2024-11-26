package com.example.taskhive.domain.task;

import com.example.taskhive.domain.list.ListEntity;
import com.example.taskhive.domain.user.UserEntity;

import java.time.LocalDate;

public record CreateTaskRequestDTO(String title, String description, String assigneeId, String listId, LocalDate dueDate) {
}
