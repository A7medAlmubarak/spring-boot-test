package com.example.test.infrastructure.database.dao;

import com.example.test.domain.entity.BorrowingRecord;
import com.example.test.domain.repository.BorrowingRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BorrowingRecordDao {
    
    private final BorrowingRecordRepository borrowingRecordRepository;
    
    @Transactional(readOnly = true)
    public Optional<BorrowingRecord> findById(Long id) {
        return borrowingRecordRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public List<BorrowingRecord> findAll() {
        return borrowingRecordRepository.findAll();
    }
    
    @Transactional
    public BorrowingRecord save(BorrowingRecord borrowingRecord) {
        return borrowingRecordRepository.save(borrowingRecord);
    }
    
    @Transactional
    public void delete(BorrowingRecord borrowingRecord) {
        borrowingRecordRepository.delete(borrowingRecord);
    }
    
    @Transactional
    public void deleteById(Long id) {
        borrowingRecordRepository.deleteById(id);
    }
     
    @Transactional(readOnly = true)
    public List<BorrowingRecord> findByPatronId(Long patronId) {
        return borrowingRecordRepository.findByPatronId(patronId);
    }
    
    @Transactional(readOnly = true)
    public List<BorrowingRecord> findByBookId(Long bookId) {
        return borrowingRecordRepository.findByBookId(bookId);
    }
    
    @Transactional(readOnly = true)
    public List<BorrowingRecord> findByBookIdAndPatronId(Long bookId, Long patronId) {
        return borrowingRecordRepository.findByBookIdAndPatronId(bookId, patronId);
    }
    
} 