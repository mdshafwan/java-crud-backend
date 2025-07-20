package com.example.metalpurity.repository;

import com.example.metalpurity.model.MetalRate;
import com.example.metalpurity.model.User;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MetalRateRepository extends MongoRepository<MetalRate, String> {
Page<User> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);
}
