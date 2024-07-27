package com.homework3.Homework3.controller;

import com.homework3.Homework3.dto.AuthorDTO;
import com.homework3.Homework3.dto.BookDTO;
import com.homework3.Homework3.dto.BookDTO;
import com.homework3.Homework3.exceptions.ResourceNotFoundException;
import com.homework3.Homework3.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/book")
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping()
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id){
        BookDTO dto = bookService.getBookById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<BookDTO> saveBookData(@RequestBody BookDTO inputData){
        BookDTO dto = bookService.saveBookData(inputData);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<BookDTO> updateBookDataById(@RequestBody BookDTO inputData, @PathVariable Long id){
        BookDTO dto = bookService.updateBookDataById(inputData, id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteBookById(@PathVariable Long id){
        Boolean dto = bookService.deleteBookById(id);
        if(dto){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(path = "/{bookId}/author/{authorId}")
    public ResponseEntity<BookDTO> updateAuthorInBook(@PathVariable Long bookId, @PathVariable Long authorId){
        BookDTO dto = bookService.updateAuthorInBook(bookId, authorId);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{title}")
    public ResponseEntity<List<BookDTO>> findBookByTitle(@PathVariable(name = "title") String title){
        return ResponseEntity.ok(bookService.findBookByTitle(title));
    }

    @GetMapping(path = "/{author}")
    public ResponseEntity<List<BookDTO>> findAllBooksByAuthor(@PathVariable String author){
        return ResponseEntity.ok(bookService.findAllBooksByAuthor(author));
    }

}
