package com.example.metalpurity.controller;

import com.example.metalpurity.model.Metal;
import com.example.metalpurity.repository.MetalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/metals")
@CrossOrigin(origins = "*") // Lock to "http://localhost:4200" in production
public class MetalController {

    @Autowired
    private MetalRepository repo;

    @GetMapping
    public List<Metal> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Metal getById(@PathVariable String id) {
        return repo.findById(id).orElse(null);
    }

    @PostMapping
    public Metal create(@RequestBody Metal metal) {
        metal.setCreatedAt(LocalDateTime.now()); // Set createdAt timestamp
        return repo.save(metal);
    }

    @PutMapping("/{id}")
    public Metal update(@PathVariable String id, @RequestBody Metal metal) {
        return repo.findById(id).map(existing -> {
            existing.setName(metal.getName());
            existing.setsymbol(metal.getsymbol());

            if (existing.getCreatedAt() == null) {
                existing.setCreatedAt(LocalDateTime.now());
            }

            return repo.save(existing);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        repo.deleteById(id);
    }

    // ✅ Filtering by date range
    @GetMapping("/filter")
    public List<Metal> filterByDateRange(
        @RequestParam(required = false) String from,
        @RequestParam(required = false) String to
    ) {
        LocalDateTime fromDate = (from != null && !from.isEmpty()) ? LocalDateTime.parse(from + "T00:00:00") : null;
        LocalDateTime toDate = (to != null && !to.isEmpty()) ? LocalDateTime.parse(to + "T23:59:59") : null;

        if (fromDate != null && toDate != null) {
            return repo.findByCreatedAtBetween(fromDate, toDate);
        } else if (fromDate != null) {
            return repo.findByCreatedAtAfter(fromDate);
        } else if (toDate != null) {
            return repo.findByCreatedAtBefore(toDate);
        } else {
            return getAll();
        }
    }
}