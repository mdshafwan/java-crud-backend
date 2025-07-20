package com.example.metalpurity.model;

import com.example.metalpurity.common.HasMutationHistory;
import com.example.metalpurity.common.MutationHistory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "metal")
public class Metal implements HasMutationHistory {

    @Id
    private String id;
    private String name;
    private String symbol;
    private LocalDateTime createdAt;

    private List<MutationHistory> mutationHistory = new ArrayList<>();

    // ✅ Now your getters and setters below will work properly
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public List<MutationHistory> getMutationHistory() {
        return mutationHistory;
    }

    @Override
    public void setMutationHistory(List<MutationHistory> history) {
        this.mutationHistory = history;
    }
}