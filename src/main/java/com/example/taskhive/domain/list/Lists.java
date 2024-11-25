package com.example.taskhive.domain.list;

import com.example.taskhive.domain.board.Board;
import com.example.taskhive.domain.task.Task;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Lists {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "list_id")
    private String id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @OneToMany(mappedBy = "lists", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

    private Integer position;
}
