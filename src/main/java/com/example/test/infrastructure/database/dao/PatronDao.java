package com.example.test.infrastructure.database.dao;

import com.example.test.domain.entity.Patron;
import com.example.test.domain.entity.User;
import com.example.test.domain.repository.PatronRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PatronDao {
    
    private final PatronRepository patronRepository;
    
    @Transactional(readOnly = true)
    public Optional<Patron> findById(Long id) {
        return patronRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public List<Patron> findAll() {
        return patronRepository.findAll();
    }
    
    @Transactional
    public Patron save(Patron patron) {
        return patronRepository.save(patron);
    }
    
    @Transactional
    public void delete(Patron patron) {
        patronRepository.delete(patron);
    }
    
    @Transactional
    public void deleteById(Long id) {
        patronRepository.deleteById(id);
    }
} 