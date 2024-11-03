package com.example.test.application.service;

import org.springframework.stereotype.Service;
import com.example.test.domain.entity.Book;
import com.example.test.infrastructure.database.dao.BookDao;
import lombok.RequiredArgsConstructor;
import java.util.List;
import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookDao bookDao;
    
    public Book findById(Long id) {
        return bookDao.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }
    
    public List<Book> findAll() {
        return bookDao.findAll();
    }
    
    public Book save(Book book) {
        return bookDao.save(book);
    }
    
    public void deleteById(Long id) {
        bookDao.deleteById(id);
    }
    
    public Book updateBook(Long id, Book bookDetails) {
        Book book = findById(id);
        
        // Only update title if it's changed
        if (!book.getTitle().equals(bookDetails.getTitle())) {
            book.setTitle(bookDetails.getTitle());
        }
        
        // Only update author if it's changed  
        if (!book.getAuthor().equals(bookDetails.getAuthor())) {
            book.setAuthor(bookDetails.getAuthor());
        }
        
        return save(book);
    }
}
