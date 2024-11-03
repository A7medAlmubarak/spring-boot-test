package com.example.test.presentation.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookRequest {
    private String title;
    private String author;
    private String isbn;
    
    @Positive(message = "Publication year must be positive")
    private Integer publicationYear;
    
    @Positive(message = "Quantity must be positive")
    private Integer quantity;
} 