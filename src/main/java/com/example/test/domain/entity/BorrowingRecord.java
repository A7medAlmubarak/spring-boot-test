package com.example.test.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "borrowing_records", schema = "00004")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    
    @ManyToOne
    @JoinColumn(name = "patron_id", nullable = false)
    private Patron patron;
    
    @Column(nullable = false)
    private LocalDate borrowingDate;
    
    @Column(nullable = true)
    private LocalDate returnDate;

}

