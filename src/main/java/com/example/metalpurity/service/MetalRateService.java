package com.example.metalpurity.service;

import com.example.metalpurity.model.MetalRate;
import com.example.metalpurity.repository.MetalRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MetalRateService {

    @Autowired
    private MetalRateRepository repository;

    public List<MetalRate> getAll() {
        return repository.findAll();
    }

    public Optional<MetalRate> getById(String id) {
        return repository.findById(id);
    }

    public MetalRate create(MetalRate metalRate) {
        return repository.save(metalRate);
    }

    public MetalRate update(String id, MetalRate updated) {
        updated.setId(id);
        return repository.save(updated);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
