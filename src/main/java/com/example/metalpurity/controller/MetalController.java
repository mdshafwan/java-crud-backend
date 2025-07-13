package com.example.metalpurity.controller;

import com.example.metalpurity.model.Metal;
import com.example.metalpurity.service.MetalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/metals")
@CrossOrigin(origins = "*")
public class MetalController {

    @Autowired
    private MetalService service;

    @GetMapping
    public List<Metal> list() {
        return service.getAll();
    }

    @PostMapping
    public Metal add(@RequestBody Metal metal) {
        return service.save(metal);
    }

    @PutMapping("/{id}")
    public Metal update(@PathVariable String id, @RequestBody Metal metal) {
        metal.setId(id);
        return service.save(metal);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}
