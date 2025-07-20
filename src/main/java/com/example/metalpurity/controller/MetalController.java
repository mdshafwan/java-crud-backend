package com.example.metalpurity.controller;

import com.example.metalpurity.common.MutationHistory;
import com.example.metalpurity.model.Metal;
import com.example.metalpurity.service.MetalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/metals")
@CrossOrigin(origins = "*") // Lock to "http://localhost:4200" in production
public class MetalController {

    @Autowired
    private MetalService service;

    @GetMapping
    public List<Metal> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Metal getById(@PathVariable String id) {
        return service.getById(id).orElse(null);
    }

    @PostMapping
    public Metal create(@RequestBody Metal metal) {
        metal.setCreatedAt(LocalDateTime.now());
        return service.create(metal, "system");
    }

    @PutMapping("/{id}")
    public Metal update(@PathVariable String id, @RequestBody Metal metal) {
        return service.update(id, metal, "system");
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id, "system");
    }

    @PostMapping("/{id}/undo")
    public ResponseEntity<Metal> undo(@PathVariable String id) {
        Metal restored = service.undo(id);
        return ResponseEntity.ok(restored);
    }

    @GetMapping("/{id}/history")
    public List<MutationHistory> getHistory(@PathVariable String id) {
        return service.getHistory(id);
    }

    @GetMapping("/filter")
    public List<Metal> filterByDateRange(
        @RequestParam(required = false) String from,
        @RequestParam(required = false) String to
    ) {
        LocalDateTime fromDate = (from != null && !from.isEmpty()) ? LocalDateTime.parse(from + "T00:00:00") : null;
        LocalDateTime toDate = (to != null && !to.isEmpty()) ? LocalDateTime.parse(to + "T23:59:59") : null;

        return service.getFilteredMetals(fromDate, toDate);
    }
}