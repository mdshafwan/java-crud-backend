package com.example.metalpurity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

/**
 * Combined filter criteria for filtering metals in search, export, and dashboards.
 */
@Schema(description = "Combined filter criteria for filtering metals")
public class FilterCriteriaDTO {

    @Schema(description = "Text search for metal name")
    private String searchText;

    @Schema(description = "Filter by metal type")
    private String metalType;

    @Schema(description = "Filter by purity level")
    private String purityLevel;

    @Schema(description = "Start date of created range")
    private LocalDate fromDate;

    @Schema(description = "End date of created range")
    private LocalDate toDate;

    @Schema(description = "Column name to sort by")
    private String sortField;

    @Schema(description = "Sort direction: asc or desc")
    private String sortDirection;

    // 🔧 Getters and Setters

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

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
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

    /**
     * Helper to check whether any filter is applied.
     */
    public boolean hasAnyFilter() {
        return (searchText != null && !searchText.isEmpty()) ||
               (metalType != null) ||
               (purityLevel != null) ||
               (fromDate != null) ||
               (toDate != null);
    }

    @Override
    public String toString() {
        return "FilterCriteriaDTO{" +
            "searchText='" + searchText + '\'' +
            ", metalType='" + metalType + '\'' +
            ", purityLevel='" + purityLevel + '\'' +
            ", fromDate=" + fromDate +
            ", toDate=" + toDate +
            ", sortField='" + sortField + '\'' +
            ", sortDirection='" + sortDirection + '\'' +
            '}';
    }
}