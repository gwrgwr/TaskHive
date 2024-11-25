package com.example.taskhive.domain.board;

import com.example.taskhive.domain.list.Lists;
import com.example.taskhive.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "board_id")
    private String id;

    @Column(unique = true)
    private String name;

    private String description;

    private String accessCode = "";

    @ManyToOne
    @JoinColumn(name = "created_by_id", nullable = false)
    private UserEntity createdBy;

    @ManyToMany
    @JoinTable(
        name = "board_collaborators",
        joinColumns = @JoinColumn(name = "board_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> collaborators;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lists> lists;


    private LocalDateTime createdAt;

    public Board(String name, String description, UserEntity user) {
        this.name = name;
        this.description = description;
        this.createdBy = user;
        this.createdAt = LocalDateTime.now();
    }
}
