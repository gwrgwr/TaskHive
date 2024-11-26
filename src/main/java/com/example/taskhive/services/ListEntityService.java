package com.example.taskhive.services;

import com.example.taskhive.domain.board.Board;
import com.example.taskhive.domain.list.CreateListEntityRequestDTO;
import com.example.taskhive.domain.list.ListEntity;
import com.example.taskhive.domain.list.UpdateListEntityRequestDTO;
import com.example.taskhive.domain.task.Task;
import com.example.taskhive.exceptions.list.ListEntityAlreadyExistsException;
import com.example.taskhive.exceptions.list.ListEntityNotFoundException;
import com.example.taskhive.repositories.ListsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListEntityService {
    private final ListsRepository listsRepository;
    private final BoardService boardService;

    public ListEntityService(ListsRepository listsRepository, BoardService boardService) {
        this.listsRepository = listsRepository;
        this.boardService = boardService;
    }

    public ListEntity findByName(String name) {
        return this.listsRepository.findByName(name);
    }

    public ListEntity save(CreateListEntityRequestDTO data) {
        ListEntity listEntity = findByName(data.name());
        if (listEntity != null) {
            throw new ListEntityAlreadyExistsException();
        }
        Board board = this.boardService.getBoardById(data.boardId());
        ListEntity list = new ListEntity(data.name(), data.position(), board);
        return this.listsRepository.save(list);
    }


    public ListEntity getListById(String listId) {
        return this.listsRepository.findById(listId).orElseThrow(ListEntityNotFoundException::new);
    }

    public List<Task> getTasks(String listId) {
        ListEntity listEntity = getListById(listId);
        return listEntity.getTasks();
    }

    public void changeListPosition(String listId, Integer newPosition) {
        ListEntity listEntity = getListById(listId);
        listEntity.setPosition(newPosition);
        this.listsRepository.save(listEntity);
    }

    public ListEntity updateListEntity (String id, UpdateListEntityRequestDTO data) {
        ListEntity listEntity = getListById(id);
        listEntity.setName(data.name());
        listEntity.setPosition(data.position());
        return this.listsRepository.save(listEntity);
    }

    public void deleteListEntity(String id) {
        this.listsRepository.deleteById(getListById(id).getId());
    }
}
