package com.example.taskhive.repositories;

import com.example.taskhive.domain.list.ListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListsRepository extends JpaRepository<ListEntity, String> {
    ListEntity findByName(String name);
}
