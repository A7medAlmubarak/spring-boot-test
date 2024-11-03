package com.example.test.application.usecase;

import com.example.test.application.service.BookService;
import com.example.test.application.service.BorrowingRecordService;
import com.example.test.application.service.PatronService;
import com.example.test.domain.entity.Book;
import com.example.test.domain.entity.BorrowingRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BorrowingRecordUseCase {
    
    private final BorrowingRecordService borrowingRecordService;
    private final BookService bookService;
    private final PatronService patronService;

    public BorrowingRecord borrowBook(Long patronId, Long bookId) {
        Book book = bookService.findById(bookId);
        
        if (!book.isAvailable()) {
            throw new IllegalStateException("Book is not available for borrowing");
        }
        
        BorrowingRecord borrowingRecord = BorrowingRecord.builder()
            .book(book)
            .patron(patronService.findById(patronId))
            .borrowingDate(LocalDate.now())
            .build();
            
        book.setAvailable(false);
        bookService.save(book);
        
        return borrowingRecordService.save(borrowingRecord);
    }
    
    public BorrowingRecord returnBook(Long bookId, Long patronId) {
        List<BorrowingRecord> records = borrowingRecordService.findByBookIdAndPatronId(bookId, patronId);
        BorrowingRecord borrowingRecord = records.stream()
            .filter(record -> record.getReturnDate() == null)
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("No active borrowing record found for this book and patron"));
        
        Book book = borrowingRecord.getBook();
        book.setAvailable(true);
        bookService.save(book);
        
        borrowingRecord.setReturnDate(LocalDate.now());
        return borrowingRecordService.save(borrowingRecord);
    }
    
    public List<BorrowingRecord> getAllBorrowingRecords() {
        return borrowingRecordService.findAll();
    }
    
    public BorrowingRecord getBorrowingRecord(Long id) {
        return borrowingRecordService.findById(id);
    }
}
