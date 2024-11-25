package com.example.taskhive.domain.file;

import com.example.taskhive.domain.attach.Attachment;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

//    @ManyToOne
//    @JoinColumn(name = "attachment_id", nullable = false)
//    private Attachment attachment;

    public FileDB(String fileName, String contentType, byte[] bytes) {
        this.name = fileName;
        this.type = contentType;
        this.data = bytes;
    }
}
