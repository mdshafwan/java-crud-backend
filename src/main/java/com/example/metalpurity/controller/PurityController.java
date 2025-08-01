package com.example.metalpurity.controller;

import com.example.metalpurity.common.MutationHistory;
import com.example.metalpurity.model.Purity;
import com.example.metalpurity.service.PurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/purities")
@CrossOrigin(origins = "*") // Lock to frontend origin in production
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
    return service.create(purity, "system");
}



    @PutMapping("/{id}")
    public Purity update(@PathVariable String id, @RequestBody Purity purity) {
    return service.update(id, purity, "system");
}

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
    service.delete(id, "system");
}
    @PostMapping("/{id}/undo")
public ResponseEntity<Purity> undo(@PathVariable String id) {
    Purity restored = service.undo(id);
    return ResponseEntity.ok(restored);
}

    

    // ✅ Filter by date range
    @GetMapping("/filter")
    public List<Purity> filterByDateRange(
        @RequestParam(required = false) String from,
        @RequestParam(required = false) String to
    ) {
        LocalDateTime fromDate = (from != null && !from.isEmpty()) ? LocalDateTime.parse(from + "T00:00:00") : null;
        LocalDateTime toDate = (to != null && !to.isEmpty()) ? LocalDateTime.parse(to + "T23:59:59") : null;

        if (fromDate != null && toDate != null) {
            return service.findByCreatedAtBetween(fromDate, toDate);
        } else if (fromDate != null) {
            return service.findByCreatedAtAfter(fromDate);
        } else if (toDate != null) {
            return service.findByCreatedAtBefore(toDate);
        } else {
            return getAll();
        }
    }
}