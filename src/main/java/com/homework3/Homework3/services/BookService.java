package com.homework3.Homework3.services;

import com.homework3.Homework3.dto.BookDTO;
import com.homework3.Homework3.entities.AuthorEntity;
import com.homework3.Homework3.entities.BookEntity;
import com.homework3.Homework3.exceptions.ResourceNotFoundException;
import com.homework3.Homework3.repositories.AuthorRepo;
import com.homework3.Homework3.repositories.BookRepo;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private BookRepo bookRepo;
    private ModelMapper modelMapper;
    private AuthorRepo authorRepo;

    public BookService(BookRepo bookRepo, ModelMapper modelMapper, AuthorRepo authorRepo) {
        this.bookRepo = bookRepo;
        this.modelMapper = modelMapper;
        this.authorRepo = authorRepo;
    }

    public List<BookDTO> getAllBooks() {
        List<BookEntity> bookEntities = bookRepo.findAll();
        if(!bookEntities.isEmpty()){
            return bookEntities
                    .stream()
                    .map(BookEntity -> modelMapper.map(BookEntity, BookDTO.class))
                    .collect(Collectors.toList());
        }else{
            throw new RuntimeException("Failed to retrieve books data");
        }
    }

    public BookDTO getBookById(Long id) {
        Optional<BookEntity> BookEntity = bookRepo.findById(id);
        if (BookEntity.isPresent()) {
            return modelMapper.map(BookEntity.get(), BookDTO.class);
        } else {
            throw new EntityNotFoundException(String.format("book with id %d is not found", id));
        }
    }


    public BookDTO saveBookData(BookDTO inputData) {
        BookEntity BookEntity = modelMapper.map(inputData, BookEntity.class);
        try {
            BookEntity book = bookRepo.save(BookEntity);
            return modelMapper.map(book, BookDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save book data", e);
        }
    }

    public BookDTO updateBookDataById(BookDTO inputData, Long id) {
            BookEntity BookEntity = modelMapper.map(inputData, BookEntity.class);
            try{
                isExistsByEmployeeId(id);
                BookEntity.setId(id);
                BookEntity book = bookRepo.save(BookEntity);
                return modelMapper.map(book, BookDTO.class);
            }catch (Exception e){
                throw new RuntimeException("Failed to update data of book", e);
            }
    }

    public Boolean deleteBookById(Long id) {
            isExistsByEmployeeId(id);
            bookRepo.deleteById(id);
            return true;
    }

    public void isExistsByEmployeeId(Long id) {
            boolean exists = bookRepo.existsById(id);
            if(!exists) throw new ResourceNotFoundException("Department not found with id: "+id);
    }

    public BookDTO updateAuthorInBook(Long bookId, Long authorId) {
        Optional<BookEntity> bookEntity = bookRepo.findById(bookId);
        Optional<AuthorEntity> authorEntity = authorRepo.findById(authorId);
        if(authorEntity.isPresent() && bookEntity.isPresent()){
            AuthorEntity author = authorEntity.get();
            BookEntity book = bookEntity.get();

            book.getAuthors().add(author);
            author.getBooks().add(book);

            bookRepo.save(book);
            authorRepo.save(author);

            return modelMapper.map(book, BookDTO.class);
        }
        else {
            throw new EntityNotFoundException("Book or Author is not found");
        }
    }

    public List<BookDTO> findBookByTitle(String title) {
        List<BookEntity> books = bookRepo.findByTitle(title);
        return books.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }


    public List<BookDTO> findAllBooksByAuthor(String author) {
        List<BookEntity> bookEntities = bookRepo.findByAuthors_Name(author);
        if(!bookEntities.isEmpty()){
            return bookEntities.stream().map(bookEntity -> modelMapper.map(bookEntity,BookDTO.class)).collect(Collectors.toList());
        }else{
            throw new EntityNotFoundException("Author name is not found");
        }
    }
}
