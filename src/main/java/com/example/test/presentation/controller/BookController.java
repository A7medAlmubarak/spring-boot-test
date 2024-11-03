package com.example.test.presentation.controller;

import com.example.test.application.usecase.BookUseCase;
import com.example.test.domain.entity.Book;
import com.example.test.presentation.dto.request.CreateBookRequest;
import com.example.test.presentation.dto.request.UpdateBookRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    
    private final BookUseCase bookUseCase;
    
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookUseCase.findAllBooks());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookUseCase.findBookById(id));
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createBook(@Valid @ModelAttribute CreateBookRequest request) {
        Book book = Book.builder()
            .title(request.getTitle())
            .author(request.getAuthor())
            .isbn(request.getIsbn()) 
            .publicationYear(request.getPublicationYear())
            .build();
            
        return ResponseEntity.ok(bookUseCase.createBook(book));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable Long id,
            @Valid @ModelAttribute UpdateBookRequest request) {
        Book bookDetails = Book.builder()
            .title(request.getTitle())
            .author(request.getAuthor()) 
            .isbn(request.getIsbn())
            .publicationYear(request.getPublicationYear())
            .build();
            
        Book updatedBook = bookUseCase.updateBook(id, bookDetails);
        return ResponseEntity.ok(updatedBook);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookUseCase.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
