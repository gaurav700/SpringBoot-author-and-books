package com.homework3.Homework3.repositories;

import com.homework3.Homework3.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findByTitle(String title);

    List<BookEntity> findByAuthors_Name(String name);

}
