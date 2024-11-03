package com.example.test.application.usecase;

import com.example.test.application.service.BookService;
import com.example.test.domain.entity.Book;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookUseCase {
    private final BookService bookService;
    
    public Book findBookById(Long id) {
        return bookService.findById(id);
    }
    
    public List<Book> findAllBooks() {
        return bookService.findAll();
    }
    
    public Book createBook(Book book) {
        return bookService.save(book);
    }
    
    @Transactional
    public Book updateBook(Long id, Book bookDetails) {
        return bookService.updateBook(id, bookDetails);
    }
    
    public void deleteBook(Long id) {
        bookService.deleteById(id);
    }
}
