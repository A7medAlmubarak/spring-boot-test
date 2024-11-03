package com.example.test.presentation.controller;

import com.example.test.domain.entity.BorrowingRecord;
import com.example.test.application.service.BorrowingRecordService;
import com.example.test.application.service.BookService;
import com.example.test.application.service.PatronService;
import com.example.test.infrastructure.logging.Loggable;
import com.example.test.presentation.dto.request.BorrowingRecordRequest;
import com.example.test.presentation.exception.ErrorResponse;
import com.example.test.application.usecase.BorrowingRecordUseCase;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/borrowing-records")
public class BorrowingRecordController {
    
    private final BorrowingRecordService borrowingRecordService;
    private final BookService bookService;
    private final PatronService patronService;
    private final BorrowingRecordUseCase borrowingRecordUseCase;

    public BorrowingRecordController(BorrowingRecordService borrowingRecordService, BookService bookService, PatronService patronService, BorrowingRecordUseCase borrowingRecordUseCase) {
        this.borrowingRecordService = borrowingRecordService;
        this.bookService = bookService;
        this.patronService = patronService;
        this.borrowingRecordUseCase = borrowingRecordUseCase;
    }

    @Loggable
    @GetMapping
    public ResponseEntity<List<BorrowingRecord>> getAllBorrowingRecords() {
        return ResponseEntity.ok(borrowingRecordService.findAll());
    }

    @Loggable
    @GetMapping("/{id}")
    public ResponseEntity<BorrowingRecord> getBorrowingRecordById(@PathVariable Long id) {
        try {
            BorrowingRecord borrowingRecord = borrowingRecordService.findById(id);
            return ResponseEntity.ok(borrowingRecord);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @Loggable
    @Transactional
    @PostMapping("/patron/{patronId}/book/{bookId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createBorrowingRecord(
            @PathVariable Long patronId,
            @PathVariable Long bookId,
            @Valid @ModelAttribute BorrowingRecordRequest request) {
        try {
            BorrowingRecord borrowingRecord = borrowingRecordUseCase.borrowBook(patronId, bookId);
            return ResponseEntity.status(HttpStatus.CREATED).body(borrowingRecord);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST.value())
                    .timestamp(LocalDateTime.now())
                    .build());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.NOT_FOUND.value())
                    .timestamp(LocalDateTime.now())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                    .message("An unexpected error occurred")
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .timestamp(LocalDateTime.now())
                    .build());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrowingRecord(@PathVariable Long id) {
        borrowingRecordService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/return/patron/{patronId}/book/{bookId}")
    public ResponseEntity<?> returnBook(
            @PathVariable Long bookId,
            @PathVariable Long patronId) {
        try {
            BorrowingRecord borrowingRecord = borrowingRecordUseCase.returnBook(bookId, patronId);
            return ResponseEntity.ok(borrowingRecord);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.NOT_FOUND.value())
                    .timestamp(LocalDateTime.now())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                    .message("An unexpected error occurred")
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .timestamp(LocalDateTime.now())
                    .build());
        }
    }
}
