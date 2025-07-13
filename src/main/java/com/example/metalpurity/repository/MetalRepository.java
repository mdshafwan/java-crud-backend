package com.example.metalpurity.repository;

import com.example.metalpurity.model.Metal;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MetalRepository extends MongoRepository<Metal, String> {
}
