package com.example.metalpurity.service;

import com.example.metalpurity.model.Purity;
import com.example.metalpurity.repository.PurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    // ✅ Date filtering logic used by /filter endpoint
    public List<Purity> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to) {
        return repository.findByCreatedAtBetween(from, to);
    }

    public List<Purity> findByCreatedAtAfter(LocalDateTime from) {
        return repository.findByCreatedAtAfter(from);
    }

    public List<Purity> findByCreatedAtBefore(LocalDateTime to) {
        return repository.findByCreatedAtBefore(to);
    }

    // ✅ Convenience method for controller — handles all cases
    public List<Purity> getFilteredPurities(LocalDateTime from, LocalDateTime to) {
        if (from != null && to != null) {
            return findByCreatedAtBetween(from, to);
        } else if (from != null) {
            return findByCreatedAtAfter(from);
        } else if (to != null) {
            return findByCreatedAtBefore(to);
        } else {
            return getAll();
        }
    }
}