package com.example.metalpurity.controller;

import com.example.metalpurity.model.MetalRate;
import com.example.metalpurity.service.MetalRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/metalRates")
@CrossOrigin(origins = "*") // ✅ Restrict to localhost:4200 in prod
public class MetalRateController {

    @Autowired
    private MetalRateService service;

    @GetMapping
    public List<MetalRate> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public MetalRate getById(@PathVariable String id) {
        return service.getById(id).orElse(null);
    }

    @PostMapping
    public MetalRate create(@RequestBody MetalRate metalRate) {
        metalRate.setCreatedAt(LocalDateTime.now()); // ✅ Timestamp added
        return service.create(metalRate);
    }

    @PutMapping("/{id}")
    public MetalRate update(@PathVariable String id, @RequestBody MetalRate metalRate) {
        return service.update(id, metalRate);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

    // ✅ NEW: Filtered endpoint
    @GetMapping("/filter")
    public List<MetalRate> filterByDateRange(
        @RequestParam(required = false) String fromDate,
        @RequestParam(required = false) String toDate
    ) {
        return service.filterByDateRange(fromDate, toDate);
    }
}