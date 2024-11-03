package com.example.test.application.service;

import org.springframework.stereotype.Service;

import com.example.test.domain.entity.Patron;
import com.example.test.infrastructure.database.dao.PatronDao;

import lombok.RequiredArgsConstructor;
import java.util.List;
import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class PatronService {
    private final PatronDao patronDao;
    
    public Patron findById(Long id) {
        return patronDao.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Patron not found"));

    }
    
    public List<Patron> findAll() {
        return patronDao.findAll();
    }
    
    public Patron save(Patron patron) {
        return patronDao.save(patron);
    }
    
    public void deleteById(Long id) {
        patronDao.deleteById(id);
    }
}
