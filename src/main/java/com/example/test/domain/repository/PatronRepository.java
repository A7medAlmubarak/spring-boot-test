package com.example.test.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.test.domain.entity.Patron;

@Repository
public interface PatronRepository extends JpaRepository<Patron, Long> {
}