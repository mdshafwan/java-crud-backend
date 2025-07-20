package com.example.metalpurity.service;

import com.example.metalpurity.model.Metal;
import com.example.metalpurity.repository.MetalRepository;
import com.example.metalpurity.common.MutationHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.time.LocalDateTime;
import java.util.List;
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

    public List<Metal> getFilteredMetals(LocalDateTime from, LocalDateTime to) {
        if (from != null && to != null) {
            return repo.findByCreatedAtBetween(from, to);
        } else if (from != null) {
            return repo.findByCreatedAtAfter(from);
        } else if (to != null) {
            return repo.findByCreatedAtBefore(to);
        } else {
            return getAll();
        }
    }

    public List<MutationHistory> getHistory(String id) {
        return repo.findById(id)
            .map(Metal::getMutationHistory)
            .orElseThrow(() -> new IllegalArgumentException("Metal not found: " + id));
    }

    public Metal create(Metal metal, String user) {
        metal.setCreatedAt(LocalDateTime.now());
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

        // Currently throws UnsupportedOperationException until you implement restore-from-map
        Metal restored = undoLastMutation(entity);
        return repo.save(restored);
    }

    public Metal save(Metal metal, String user) {
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

    // Convert ISO string to LocalDateTime
    String createdAtStr = (String) previousState.get("createdAt");
    if (createdAtStr != null) {
        restored.setCreatedAt(LocalDateTime.parse(createdAtStr));
    }

    // Patch mutation history back in and log the undo
    restored.setMutationHistory(history);
    Map<String, Object> currentSnapshot = mapper.convertValue(entity, new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {});
    history.add(new MutationHistory(java.time.Instant.now(), "UNDO", "system", currentSnapshot));

    return restored;
}
}