package com.example.test.application.usecase;

import com.example.test.application.service.JwtService;
import com.example.test.application.service.PatronService;
import com.example.test.application.service.UserService;
import com.example.test.domain.entity.Patron;
import com.example.test.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserUseCase {
    
    private final UserService userService;
    private final PatronService patronService;
    private final JwtService jwtService;
    
    @Transactional
    public Map<String, Object> registerUser(User userRequest , Patron patron) {
        User savedUser = userService.createUser(userRequest);

        patron.setUser(savedUser);
        patronService.save(patron);
        String token = jwtService.generateToken(savedUser.getEmail());
        
        return createAuthResponse(savedUser, token);
    }
    
    @Transactional(readOnly = true)
    public Map<String, Object> loginUser(String email, String password) {
        if (!userService.verifyPassword(email, password)) {
            throw new BadCredentialsException("Invalid email or password");
        }
        
        User user = userService.getUserByEmail(email);
        String token = jwtService.generateToken(email);
        
        return createAuthResponse(user, token);
    }
    
    @Transactional
    public User updateUserProfile(Long userId, User userDetails) {
        return userService.updateUser(userId, userDetails);
    }
    
    @Transactional
    public void changeUserPassword(Long userId, String oldPassword, String newPassword) {
        userService.changePassword(userId, oldPassword, newPassword);
    }
    
    @Transactional(readOnly = true)
    public User getUserProfile(Long userId) {
        return userService.getUserById(userId);
    }
    
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    
    @Transactional
    public void deleteUserAccount(Long userId) {
        userService.deleteUser(userId);
    }
    
    private Map<String, Object> createAuthResponse(User user, String token) {
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", createUserResponse(user));
        return response;
    }
    
    private Map<String, Object> createUserResponse(User user) {
        Map<String, Object> userResponse = new HashMap<>();
        userResponse.put("id", user.getId());
        userResponse.put("email", user.getEmail());
        return userResponse;
    }
}
