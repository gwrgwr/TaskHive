package com.example.taskhive.domain.file;

import com.example.taskhive.domain.task.Task;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileDB {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "file_id")
    private String id;

    private String name;

    private String type;

    @Lob
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    public FileDB(String fileName, String contentType, byte[] bytes, Task task) {
        this.name = fileName;
        this.type = contentType;
        this.data = bytes;
        this.task = task;
    }
}
