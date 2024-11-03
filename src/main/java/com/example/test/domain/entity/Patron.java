package com.example.test.domain.entity;

import java.util.List;

import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "patrons", schema = "00002")
@DependsOn("users") // This indicates that patrons depends on users
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patron {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" ,  nullable = false)
    private User user;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "patron", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BorrowingRecord> borrowingRecords;


   /* @OneToOne
    @JoinColumn(name = "user_id", nullable = false) // Ensure this is not nullable if required
    private User user;
*/

}