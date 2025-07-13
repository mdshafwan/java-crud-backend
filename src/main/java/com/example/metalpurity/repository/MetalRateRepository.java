package com.example.metalpurity.repository;

import com.example.metalpurity.model.MetalRate;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MetalRateRepository extends MongoRepository<MetalRate, String> {
}
