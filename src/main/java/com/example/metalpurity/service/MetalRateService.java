package com.example.metalpurity.service;

import com.example.metalpurity.model.MetalRate;
import com.example.metalpurity.repository.MetalRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MetalRateService {

    @Autowired
    private MetalRateRepository repository;

    public List<MetalRate> getAll() {
        return repository.findAll();
    }

    public java.util.Optional<MetalRate> getById(String id) {
        return repository.findById(id);
    }

    public MetalRate create(MetalRate metalRate) {
        return repository.save(metalRate);
    }

    public MetalRate update(String id, MetalRate updated) {
        updated.setId(id);
        return repository.save(updated);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    // ✅ NEW: Filter rates by date
    public List<MetalRate> filterByDateRange(String fromDateStr, String toDateStr) {
        List<MetalRate> allRates = repository.findAll();

        return allRates.stream()
            .filter(rate -> {
                if (rate.getEffectiveDate() == null) return false;

                LocalDate rateDate;
                try {
                    rateDate = LocalDate.parse(rate.getEffectiveDate());
                } catch (Exception e) {
                    return false;
                }

                LocalDate from = (fromDateStr != null && !fromDateStr.isEmpty()) ? LocalDate.parse(fromDateStr) : null;
                LocalDate to   = (toDateStr != null && !toDateStr.isEmpty())   ? LocalDate.parse(toDateStr)   : null;

                boolean afterFrom = (from == null || !rateDate.isBefore(from));
                boolean beforeTo  = (to == null || !rateDate.isAfter(to));

                return afterFrom && beforeTo;
            })
            .collect(Collectors.toList());
    }
}