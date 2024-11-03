package com.example.test.application.service;

import com.example.test.domain.entity.User;
import com.example.test.infrastructure.database.dao.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    
    public User createUser(User user) {
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        
        if (userDao.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        return userDao.save(newUser);
    }
    public User updateUser(Long id, User userDetails) {
        User user = userDao.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
            
        
        // Only update email if it's changed and not already taken
        if (!user.getEmail().equals(userDetails.getEmail())) {
            if (userDao.existsByEmail(userDetails.getEmail())) {
                throw new IllegalArgumentException("Email already exists");
            }
            user.setEmail(userDetails.getEmail());
        }
        
        // Only update password if provided
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }
        
        return userDao.save(user);
    }
    
    @Cacheable(value = "users", key = "#id")
    public User getUserById(Long id) {
        return userDao.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
    
    public User getUserByEmail(String email) {
        return userDao.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
    
    public List<User> getAllUsers() {
        return userDao.findAll();
    }
    
    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(Long id) {
        if (!userDao.findById(id).isPresent()) {
            throw new EntityNotFoundException("User not found");
        }
        userDao.deleteById(id);
    }
    
    public boolean verifyPassword(String email, String password) {
        User user = getUserByEmail(email);
        return passwordEncoder.matches(password, user.getPassword());
    }
    
    public void changePassword(Long id, String oldPassword, String newPassword) {
        User user = getUserById(id);
        
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Invalid old password");
        }
        
        user.setPassword(passwordEncoder.encode(newPassword));
        userDao.save(user);
    }
}
