package com.example.taskhive.controllers;

import com.example.taskhive.annotations.list.*;
import com.example.taskhive.domain.list.CreateListEntityRequestDTO;
import com.example.taskhive.domain.list.ListEntity;
import com.example.taskhive.domain.list.UpdateListEntityRequestDTO;
import com.example.taskhive.domain.task.Task;
import com.example.taskhive.services.ListEntityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/list")
@Tag(name = "List Entity", description = "List Entity Controller")
public class ListEntityController {
    private final ListEntityService listEntityService;

    public ListEntityController(ListEntityService listEntityService) {
        this.listEntityService = listEntityService;
    }

    @GetMapping("name/{name}")
    @GetListAnnotation
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ListEntity getListEntityByName(@PathVariable String name) {
        return this.listEntityService.findByName(name);
    }

    @GetListAnnotation
    @GetMapping(value = "id/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    ListEntity getListEntityById(@PathVariable String id) {
        return this.listEntityService.getListById(id);
    }

    @GetListAnnotation
    @GetMapping(value = "tasks/{listId}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<Task> getTasks(@PathVariable String listId) {
        return this.listEntityService.getTasks(listId);
    }

    @PostMapping
    @CreateListEntityAnnotation
    @PreAuthorize("hasAuthority('SCOPE_STAFF')")
    public ListEntity createListEntity(@RequestBody CreateListEntityRequestDTO data) {
        return this.listEntityService.save(data);
    }

    @PutMapping(value = "{id}")
    @UpdateListAnnotation
    @PreAuthorize("hasAuthority('SCOPE_STAFF')")
    public ListEntity updateListEntity(@PathVariable String id, @RequestBody UpdateListEntityRequestDTO data) {
        return this.listEntityService.updateListEntity(id, data);
    }

    @PutMapping(value = "position/{listId}/{newPosition}")
    @ChangeListPositionAnnotation
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public void changeListPosition(@PathVariable String listId, @PathVariable Integer newPosition) {
        this.listEntityService.changeListPosition(listId, newPosition);
    }

    @DeleteMapping(value = "{id}")
    @DeleteListEntityAnnotation
    @PreAuthorize("hasAuthority('SCOPE_STAFF')")
    public void deleteListEntity(@PathVariable String id) {
        this.listEntityService.deleteListEntity(id);
    }
}
