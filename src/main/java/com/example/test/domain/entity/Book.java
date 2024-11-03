package com.example.test.domain.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.List;

import jakarta.persistence.Column;
import lombok.*;

@Entity
@Table(name = "books", schema = "00003")
@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String author;
    
    @Column(name = "publicationYear", nullable = false)
    private int publicationYear;
    
    @Column(nullable = false, unique = true)
    private String isbn;

    @OneToMany(mappedBy = "book")
    private List<BorrowingRecord> borrowingRecords;

    @Column(nullable = false)
    private boolean available = true;

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
} 