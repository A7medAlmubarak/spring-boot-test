package com.example.test.application.service;

import org.springframework.stereotype.Service;
import com.example.test.domain.entity.BorrowingRecord;
import com.example.test.infrastructure.database.dao.BorrowingRecordDao;
import lombok.RequiredArgsConstructor;
import java.util.List;
import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class BorrowingRecordService {
    private final BorrowingRecordDao borrowingRecordDao;
    
    public BorrowingRecord findById(Long id) {
        return borrowingRecordDao.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("BorrowingRecord not found"));
    }
    
    public List<BorrowingRecord> findAll() {
        return borrowingRecordDao.findAll();
    }
    
    public BorrowingRecord save(BorrowingRecord borrowingRecord) {
        return borrowingRecordDao.save(borrowingRecord);
    }
    
    public void deleteById(Long id) {
        borrowingRecordDao.deleteById(id);
    }
    
    public List<BorrowingRecord> findByBookIdAndPatronId(Long bookId, Long patronId) {
        return borrowingRecordDao.findByBookIdAndPatronId(bookId, patronId);
    }
}
