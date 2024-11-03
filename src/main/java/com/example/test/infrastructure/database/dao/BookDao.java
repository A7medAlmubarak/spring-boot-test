package com.example.test.infrastructure.database.dao;

import com.example.test.domain.entity.Book;
import com.example.test.domain.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookDao {
    
    private final BookRepository bookRepository;
    
    @Transactional(readOnly = true)
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
    
    @Transactional
    public Book save(Book book) {
        return bookRepository.save(book);
    }
    
    @Transactional
    public void delete(Book book) {
        bookRepository.delete(book);
    }
    
    @Transactional
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
    
} 