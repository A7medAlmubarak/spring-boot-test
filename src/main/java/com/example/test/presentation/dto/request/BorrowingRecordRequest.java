package com.example.test.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingRecordRequest {

    @NotNull(message = "Book ID is required")
    private Long bookId;

    @NotNull(message = "Patron ID is required")
    private Long patronId;

    @NotNull(message = "Borrowing date is required")
    private LocalDate borrowingDate;

} 