package com.example.taskhive.domain.list;

import com.example.taskhive.domain.board.Board;
import com.example.taskhive.domain.task.Task;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lists")
public class ListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "list_id")
    private String id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @OneToMany(mappedBy = "listEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

    private Integer position;

    public ListEntity(String name, Integer position, Board board) {
        this.name = name;
        this.position = position;
        this.board = board;
    }
}
