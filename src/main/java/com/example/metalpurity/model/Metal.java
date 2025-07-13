package com.example.metalpurity.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "metal")
public class Metal {
    @Id
    private String id;
    private String name;
    private String symbol;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getsymbol() { return symbol; }
    public void setsymbol(String symbol) { this.symbol = symbol; }
}
