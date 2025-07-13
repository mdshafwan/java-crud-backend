package com.example.metalpurity.repository;

import com.example.metalpurity.model.Purity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PurityRepository extends MongoRepository<Purity, String> {


}
