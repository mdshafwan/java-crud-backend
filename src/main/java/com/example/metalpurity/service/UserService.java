package com.example.metalpurity.service;

import com.example.metalpurity.model.User;
import com.example.metalpurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    public List<User> getAll() { return repo.findAll(); }

    public User save(User u) { return repo.save(u); }

    public void delete(String id) { repo.deleteById(id); }
}