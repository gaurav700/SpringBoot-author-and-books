package com.homework3.Homework3.services;

import com.homework3.Homework3.dto.AuthorDTO;
import com.homework3.Homework3.entities.AuthorEntity;
import com.homework3.Homework3.entities.BookEntity;
import com.homework3.Homework3.exceptions.ResourceNotFoundException;
import com.homework3.Homework3.repositories.AuthorRepo;
import com.homework3.Homework3.repositories.BookRepo;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private AuthorRepo authorRepo;
    private ModelMapper modelMapper;
    private BookRepo bookRepo;

    public AuthorService(AuthorRepo authorRepo, ModelMapper modelMapper, BookRepo bookRepo) {
        this.authorRepo = authorRepo;
        this.modelMapper = modelMapper;
        this.bookRepo = bookRepo;
    }

    public List<AuthorDTO> getAllAuthors() {
        List<AuthorEntity> authorEntities = authorRepo.findAll();
        if(!authorEntities.isEmpty()){
            return authorEntities
                    .stream()
                    .map(authorEntity -> modelMapper.map(authorEntity, AuthorDTO.class))
                    .collect(Collectors.toList());
        }else{
            throw new RuntimeException("Failed to retrieve authors data");
        }

    }

    public AuthorDTO saveAuthorData(AuthorDTO inputData) {
        AuthorEntity authorEntity = modelMapper.map(inputData, AuthorEntity.class);
        try {
            AuthorEntity author = authorRepo.save(authorEntity);
            return modelMapper.map(author, AuthorDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save author data", e);
        }
    }

    public AuthorDTO getAuthorById(Long id) {
        Optional<AuthorEntity> authorEntity = authorRepo.findById(id);
        if (authorEntity.isPresent()) {
            return modelMapper.map(authorEntity.get(), AuthorDTO.class);
        } else {
            throw new EntityNotFoundException(String.format("Author with id %d is not found", id));
        }
    }

    public AuthorDTO updateAuthorDataById(AuthorDTO inputData, Long id) {
        AuthorEntity authorEntity = modelMapper.map(inputData, AuthorEntity.class);
        try{
            isExistsByEmployeeId(id);
            authorEntity.setId(id);
            AuthorEntity author = authorRepo.save(authorEntity);
            return modelMapper.map(author, AuthorDTO.class);
        }catch (Exception e){
            throw new RuntimeException("Failed to update data of author", e);
        }
    }

    public Boolean deleteAuthorById(Long id) {
        isExistsByEmployeeId(id);
        authorRepo.deleteById(id);
        return true;
    }


    public void isExistsByEmployeeId(Long id) {
        boolean exists = authorRepo.existsById(id);
        if(!exists) throw new ResourceNotFoundException("Author not found with id: "+id);
    }

    public AuthorDTO updateBookInAuthor(Long authorId, Long bookId) {
        Optional<AuthorEntity> authorEntity = authorRepo.findById(authorId);
        Optional<BookEntity> bookEntity = bookRepo.findById(bookId);

        if(authorEntity.isPresent() && bookEntity.isPresent()){
            AuthorEntity author = authorEntity.get();
            BookEntity book = bookEntity.get();

            author.getBooks().add(book);
            book.getAuthors().add(author);

            authorRepo.save(author);
            bookRepo.save(book);

            return modelMapper.map(author, AuthorDTO.class);
        }
        else {
            throw  new EntityNotFoundException("Author or Book is not found");
        }
    }

    public List<AuthorDTO> findAuthorByName(String author) {
        List<AuthorEntity> authorEntity = authorRepo.findByName(author);
        if(!authorEntity.isEmpty()){
            return authorEntity.stream().map(authorEntity1 -> modelMapper.map(authorEntity1, AuthorDTO.class)).collect(Collectors.toList());
        }else{
            throw new EntityNotFoundException("Author name is not found");
        }
    }

}
