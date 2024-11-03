package com.example.test.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.test.domain.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}