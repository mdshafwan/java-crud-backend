package com.example.metalpurity.controller;

import com.example.metalpurity.common.MutationHistory;
import com.example.metalpurity.dto.FilterCriteriaDTO;
import com.example.metalpurity.model.Metal;
import com.example.metalpurity.service.MetalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
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

    @PostMapping("/export")
    public ResponseEntity<Resource> exportMetalsToCSV(@RequestBody FilterCriteriaDTO criteria) {
        System.out.println("🛠️ Received FilterCriteriaDTO:");
        System.out.println("searchText=" + criteria.getSearchText());
        System.out.println("fromDate=" + criteria.getFromDate());
        System.out.println("toDate=" + criteria.getToDate());
        System.out.println("sortField=" + criteria.getSortField());
        System.out.println("sortDirection=" + criteria.getSortDirection());

        List<Metal> metals = service.getFilteredMetals(criteria);
        System.out.println("Exporting " + metals.size() + " metals:");
        metals.forEach(m -> System.out.printf("Export: %s, %s, %s%n",
                m.getName(), m.getSymbol(), m.getCreatedAt()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (PrintWriter writer = new PrintWriter(out)) {
            writer.println("Name,Symbol,CreatedDate");

            for (Metal metal : metals) {
                writer.printf("%s,%s,%s%n",
                    metal.getName(),
                    metal.getSymbol(),
                    metal.getCreatedAt() != null ? metal.getCreatedAt().toString() : ""
                );
            }
        }

        ByteArrayResource resource = new ByteArrayResource(out.toByteArray());
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=metals.csv")
            .contentType(MediaType.parseMediaType("text/csv"))
            .body(resource);
    }
}