package com.example.test.infrastructure.database.dao;

import com.example.test.domain.entity.User;
import com.example.test.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDao {
    
    private final UserRepository userRepository;
    
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }
    
    @Transactional
    public void delete(User user) {
        userRepository.delete(user);
    }
    
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
} 