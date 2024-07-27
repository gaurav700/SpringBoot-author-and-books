package com.homework3.Homework3.repositories;

import com.homework3.Homework3.entities.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepo extends JpaRepository<AuthorEntity, Long> {

    List<AuthorEntity> findByName(String name);

}
