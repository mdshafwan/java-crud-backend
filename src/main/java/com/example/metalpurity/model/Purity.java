package com.example.metalpurity.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Purity")
public class Purity {

    @Id
    private String id;
    private String name;
    private double value;

    // Constructors
    public Purity() {}

    public Purity(String name, double value) {
        this.name = name;
        this.value = value;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }
}