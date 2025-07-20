package com.example.metalpurity.repository;

import com.example.metalpurity.model.Purity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PurityRepository extends MongoRepository<Purity, String> {

    // 🔍 Search by name (case-insensitive, paged)
    Page<Purity> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // 📆 Date range filtering (paged)
    Page<Purity> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);

    // 📆 Date range filtering (non-paged, useful for export or raw lists)
    List<Purity> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);
    List<Purity> findByCreatedAtAfter(LocalDateTime from);
    List<Purity> findByCreatedAtBefore(LocalDateTime to);
}