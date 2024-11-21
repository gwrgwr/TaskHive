package com.example.taskhive.domain.board;

import com.example.taskhive.domain.list.Lists;
import com.example.taskhive.domain.user.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "created_by_id", nullable = false)
    private Users createdBy;

    @ManyToMany
    @JoinTable(
        name = "board_collaborators",
        joinColumns = @JoinColumn(name = "board_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<Users> collaborators;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lists> lists;

    private LocalDateTime createdAt;
}
