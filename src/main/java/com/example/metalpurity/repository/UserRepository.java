package com.example.metalpurity.repository;

import com.example.metalpurity.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Page<User> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
        String name, String email, Pageable pageable
    );

    // ✅ Newly added date range method
    Page<User> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);
}