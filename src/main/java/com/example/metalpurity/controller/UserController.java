package com.example.metalpurity.controller;

import com.example.metalpurity.model.User;
import com.example.metalpurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository repo;

    @GetMapping
    public List<User> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable String id) {
        return repo.findById(id).orElse(null);
    }

    @PostMapping
    public User create(@RequestBody User user) {
        user.setCreatedAt(LocalDateTime.now());  // ✅ Set createdAt timestamp
        return repo.save(user);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable String id, @RequestBody User user) {
        return repo.findById(id).map(existing -> {
            existing.setName(user.getName());
            existing.setEmail(user.getEmail());

            // 🛡️ Optional: Set createdAt if missing
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
}