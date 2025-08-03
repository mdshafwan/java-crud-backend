package com.example.metalpurity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Combined filter criteria for filtering metals")
public class FilterCriteriaDTO {

    @Schema(description = "Text search for metal name", example = "Gold")
    private String searchText;

    @Schema(description = "Filter by metal type", example = "Precious")
    private String metalType;

    @Schema(description = "Filter by purity level", example = "99.9%")
    private String purityLevel;

    @Schema(description = "Start date-time of created range", example = "2025-08-01T00:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fromDate;

    @Schema(description = "End date-time of created range", example = "2025-08-03T23:59:59")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime toDate;

    @Schema(description = "Column name to sort by", example = "createdAt")
    private String sortField;

    @Schema(description = "Sort direction: asc or desc", example = "asc")
    private String sortDirection;

    // Getters and Setters

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getMetalType() {
        return metalType;
    }

    public void setMetalType(String metalType) {
        this.metalType = metalType;
    }

    public String getPurityLevel() {
        return purityLevel;
    }

    public void setPurityLevel(String purityLevel) {
        this.purityLevel = purityLevel;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateTime getToDate() {
        return toDate;
    }

    public void setToDate(LocalDateTime toDate) {
        this.toDate = toDate;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    
        
    }
}