package com.example.metalpurity.repository;

import com.example.metalpurity.model.Metal;
import com.example.metalpurity.repository.custom.MetalRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MetalRepository extends MongoRepository<Metal, String>, MetalRepositoryCustom {
    Page<Metal> findByNameContainingIgnoreCaseOrSymbolContainingIgnoreCase(
        String name, String symbol, Pageable pageable);

    Page<Metal> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);
    List<Metal> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);
    List<Metal> findByCreatedAtAfter(LocalDateTime from);
    List<Metal> findByCreatedAtBefore(LocalDateTime to);
}