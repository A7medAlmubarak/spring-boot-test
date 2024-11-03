package com.example.test.presentation.controller;

import com.example.test.application.usecase.UserUseCase;
import com.example.test.presentation.dto.request.LoginRequest;
import com.example.test.presentation.dto.request.RegisterRequest;
import com.example.test.presentation.dto.request.UpdateUserRequest;
import com.example.test.presentation.dto.response.UserResponse;
import com.example.test.domain.entity.Patron;
import com.example.test.domain.entity.User;
import com.example.test.infrastructure.logging.Loggable;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserUseCase userUseCase;

    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @Loggable
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @ModelAttribute RegisterRequest request) {
        Patron patron = Patron.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName()) 
            .phoneNumber(request.getPhoneNumber())
            .build();

        User user = User.builder()
            .email(request.getEmail())
            .password(request.getPassword())
            .patron(patron)
            .build();

        return ResponseEntity.ok(userUseCase.registerUser(user , patron));
    }
    @Loggable
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @ModelAttribute LoginRequest request) {
        try {
            return ResponseEntity.ok(userUseCase.loginUser(request.getEmail(), request.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", e.getMessage()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "User not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Login failed"));
        }
    }
    
    @Loggable
    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getProfile(@AuthenticationPrincipal long userId) {
        User user = userUseCase.getUserProfile(userId);
        return ResponseEntity.ok(UserResponse.fromEntity(user));
    }

    @PutMapping("/profile")
    public ResponseEntity<UserResponse> updateProfile(
            @AuthenticationPrincipal long userId,
            @Valid @ModelAttribute UpdateUserRequest request) {
        User userDetails = User.builder()
            .email(request.getEmail())
            .build();
            
        User updatedUser = userUseCase.updateUserProfile(userId, userDetails);
        return ResponseEntity.ok(UserResponse.fromEntity(updatedUser));
    }


    @DeleteMapping("/profile")
    public ResponseEntity<Void> deleteAccount(@AuthenticationPrincipal long userId) {
        userUseCase.deleteUserAccount(userId);
        return ResponseEntity.ok().build();
    }

    // Admin endpoints
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userUseCase.getAllUsers().stream()
            .map(UserResponse::fromEntity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }
}
