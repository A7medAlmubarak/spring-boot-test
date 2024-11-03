package com.example.test.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.test.domain.entity.BorrowingRecord;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {
    List<BorrowingRecord> findByPatronId(Long patronId);
    List<BorrowingRecord> findByBookId(Long bookId);
    List<BorrowingRecord> findByBookIdAndPatronId(Long bookId, Long patronId);
}
