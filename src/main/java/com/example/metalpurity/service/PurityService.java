package com.example.metalpurity.service;

import com.example.metalpurity.model.Purity;
import com.example.metalpurity.repository.PurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurityService {

    @Autowired
    private PurityRepository repository;

    public List<Purity> getAll() {
        return repository.findAll();
    }

    public Optional<Purity> getById(String id) {
        return repository.findById(id);
    }

    public Purity create(Purity purity) {
        return repository.save(purity);
    }

    public Purity update(String id, Purity updated) {
        updated.setId(id);
        return repository.save(updated);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
