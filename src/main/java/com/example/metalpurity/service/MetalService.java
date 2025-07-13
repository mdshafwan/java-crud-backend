package com.example.metalpurity.service;

import com.example.metalpurity.model.Metal;
import com.example.metalpurity.repository.MetalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetalService {
    @Autowired
    private MetalRepository repo;

    public List<Metal> getAll() { return repo.findAll(); }
    public Metal save(Metal m) { return repo.save(m); }
    public void delete(String id) { repo.deleteById(id); }
}
