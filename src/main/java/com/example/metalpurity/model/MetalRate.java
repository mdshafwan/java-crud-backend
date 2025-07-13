package com.example.metalpurity.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "metal_rates")
public class MetalRate {

    @Id
    private String id;

    private String metalId;
    private String purityId;
    private double rate;
    private String effectiveDate;

    public MetalRate() {}

    public MetalRate(String metalId, String purityId, double rate, String effectiveDate) {
        this.metalId = metalId;
        this.purityId = purityId;
        this.rate = rate;
        this.effectiveDate = effectiveDate;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMetalId() { return metalId; }
    public void setMetalId(String metalId) { this.metalId = metalId; }

    public String getPurityId() { return purityId; }
    public void setPurityId(String purityId) { this.purityId = purityId; }

    public double getRate() { return rate; }
    public void setRate(double rate) { this.rate = rate; }

    public String getEffectiveDate() { return effectiveDate; }
    public void setEffectiveDate(String effectiveDate) { this.effectiveDate = effectiveDate; }
}