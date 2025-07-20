package com.example.metalpurity.model;

import java.time.LocalDateTime;
import java.util.List;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.metalpurity.common.HasMutationHistory;
import com.example.metalpurity.common.MutationHistory;

@Document(collection = "Purity")
public class Purity implements HasMutationHistory
{

    @Id
    private String id;
    private String name;
    private double value;
    private LocalDateTime createdAt;
    private List<MutationHistory> mutationHistory;


    
    // Constructors
    public Purity() {}

    public Purity(String name, double value) {
        this.name = name;
        this.value = value;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
   

    @Override
    public List<MutationHistory> getMutationHistory() {
        return mutationHistory;
    }

    @Override
    public void setMutationHistory(List<MutationHistory> history) {
        this.mutationHistory = history;
    }
}

    