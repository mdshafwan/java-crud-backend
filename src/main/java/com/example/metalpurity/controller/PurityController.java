package com.example.metalpurity.controller;

import com.example.metalpurity.model.Purity;
import com.example.metalpurity.service.PurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purity")
@CrossOrigin(origins = "*")
public class PurityController {

    @Autowired
    private PurityService service;

    @GetMapping
    public List<Purity> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Purity getById(@PathVariable String id) {
        return service.getById(id).orElse(null);
    }

    @PostMapping
    public Purity create(@RequestBody Purity purity) {
        return service.create(purity);
    }

    @PutMapping("/{id}")
    public Purity update(@PathVariable String id, @RequestBody Purity purity) {
        return service.update(id, purity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}
