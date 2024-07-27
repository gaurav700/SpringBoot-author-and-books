package com.homework3.Homework3.controller;

import com.homework3.Homework3.dto.AuthorDTO;
import com.homework3.Homework3.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/author")
public class AuthorController {

    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;

    }

    @GetMapping()
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id){
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> saveAuthorData(@RequestBody AuthorDTO inputData){
        AuthorDTO dto = authorService.saveAuthorData(inputData);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<AuthorDTO> updateAuthorDataById(@RequestBody AuthorDTO inputData, @PathVariable Long id){
        AuthorDTO dto = authorService.updateAuthorDataById(inputData, id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteAuthorById(@PathVariable Long id){
        Boolean dto = authorService.deleteAuthorById(id);
        if(dto){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(path = "/{authorId}/book/{bookId}")
    public ResponseEntity<AuthorDTO> updateBookInAuthor(@PathVariable(name = "authorId") Long authorId, @PathVariable(name = "bookId") Long bookId){
        AuthorDTO dto = authorService.updateBookInAuthor(authorId, bookId);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{author}")
    public ResponseEntity<List<AuthorDTO>> findAuthorByName(@PathVariable String author){
        return ResponseEntity.ok(authorService.findAuthorByName(author));
    }
}
