package com.example.metalpurity.service;

import com.example.metalpurity.model.Metal;
import com.example.metalpurity.repository.MetalRepository;
import com.example.metalpurity.common.MutationHistory;
import com.example.metalpurity.dto.FilterCriteriaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MetalService extends BaseMutationService<Metal> {

    @Autowired
    private MetalRepository repo;

    public List<Metal> getAll() {
        return repo.findAll();
    }

    public Optional<Metal> getById(String id) {
        return repo.findById(id);
    }

    public List<Metal> getFilteredMetals(FilterCriteriaDTO criteria) {
        System.out.println("🧪 FilterCriteriaDTO received in service:");
        System.out.println("searchText = " + criteria.getSearchText());
        System.out.println("fromDate   = " + criteria.getFromDate());
        System.out.println("toDate     = " + criteria.getToDate());
        System.out.println("sortField  = " + criteria.getSortField());
        System.out.println("sortDir    = " + criteria.getSortDirection());
        System.out.println("metalType  = " + criteria.getMetalType());
        System.out.println("purityLvl  = " + criteria.getPurityLevel());

        List<Metal> result = repo.findWithFilters(criteria);
        System.out.println("🎯 Filtered metals fetched: " + result.size());
        return result;
    }

    public List<MutationHistory> getHistory(String id) {
        return repo.findById(id)
            .map(Metal::getMutationHistory)
            .orElseThrow(() -> new IllegalArgumentException("Metal not found: " + id));
    }

    public Metal create(Metal metal, String user) {
        if (metal.getCreatedAt() == null) {
            metal.setCreatedAt(LocalDateTime.now());
        }
        recordMutation(metal, "CREATE", user);
        return repo.save(metal);
    }

    public Metal update(String id, Metal patch, String user) {
        Metal entity = repo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Metal not found: " + id));

        recordMutation(entity, "UPDATE", user);
        entity.setName(patch.getName());
        entity.setSymbol(patch.getSymbol());

        return repo.save(entity);
    }

    public void delete(String id, String user) {
        Metal entity = repo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Metal not found: " + id));
        recordMutation(entity, "DELETE", user);
        repo.deleteById(id);
    }

    public Metal undo(String id) {
        Metal entity = repo.findById(id)
            .orElseThrow(() -> new IllegalStateException("Metal not found: " + id));
        Metal restored = undoLastMutation(entity);
        return repo.save(restored);
    }

    public Metal save(Metal metal, String user) {
        if (metal.getCreatedAt() == null) {
            metal.setCreatedAt(LocalDateTime.now());
        }
        recordMutation(metal, "CREATE", user);
        return repo.save(metal);
    }

    @Override
    protected Metal undoLastMutation(Metal entity) {
        List<MutationHistory> history = entity.getMutationHistory();
        if (history == null || history.isEmpty()) {
            throw new IllegalStateException("No mutations to undo");
        }

        MutationHistory last = history.remove(history.size() - 1);
        Map<String, Object> previousState = last.getPreviousState();

        Metal restored = new Metal();
        restored.setId((String) previousState.get("id"));
        restored.setName((String) previousState.get("name"));
        restored.setSymbol((String) previousState.get("symbol"));

        String createdAtStr = (String) previousState.get("createdAt");
        if (createdAtStr != null) {
            restored.setCreatedAt(LocalDateTime.parse(createdAtStr));
        }

        restored.setMutationHistory(history);
        Map<String, Object> currentSnapshot = mapper.convertValue(entity,
            new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {});
        history.add(new MutationHistory(java.time.Instant.now(), "UNDO", "system", currentSnapshot));

        return restored;
    }
}