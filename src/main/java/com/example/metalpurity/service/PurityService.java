package com.example.metalpurity.service;

import com.example.metalpurity.model.Purity;
import com.example.metalpurity.repository.PurityRepository;
import com.example.metalpurity.common.MutationHistory;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PurityService extends BaseMutationService<Purity> {

    @Autowired
    private PurityRepository repository;

    // 🔄 Standard CRUD with mutation tracking

    public List<Purity> getAll() {
        return repository.findAll();
    }

    public Optional<Purity> getById(String id) {
        return repository.findById(id);
    }

    public Purity create(Purity purity, String user) {
        purity.setCreatedAt(LocalDateTime.now());
        recordMutation(purity, "CREATE", user);
        return repository.save(purity);
    }

    public Purity update(String id, Purity updated, String user) {
        Purity entity = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Purity not found: " + id));

        recordMutation(entity, "UPDATE", user);

        entity.setName(updated.getName());
        entity.setValue(updated.getValue());

        return repository.save(entity);
    }

    public void delete(String id, String user) {
        Purity entity = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Purity not found: " + id));

        recordMutation(entity, "DELETE", user);
        repository.deleteById(id);
    }

    public Purity undo(String id) {
        Purity entity = repository.findById(id)
            .orElseThrow(() -> new IllegalStateException("Purity not found: " + id));

        Purity restored = undoLastMutation(entity);
        return repository.save(restored);
    }

    public List<MutationHistory> getHistory(String id) {
        return repository.findById(id)
            .map(Purity::getMutationHistory)
            .orElseThrow(() -> new IllegalArgumentException("Purity not found: " + id));
    }

    // 📅 Date filtering logic

    public List<Purity> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to) {
        return repository.findByCreatedAtBetween(from, to);
    }

    public List<Purity> findByCreatedAtAfter(LocalDateTime from) {
        return repository.findByCreatedAtAfter(from);
    }

    public List<Purity> findByCreatedAtBefore(LocalDateTime to) {
        return repository.findByCreatedAtBefore(to);
    }

    public List<Purity> getFilteredPurities(LocalDateTime from, LocalDateTime to) {
        if (from != null && to != null) {
            return findByCreatedAtBetween(from, to);
        } else if (from != null) {
            return findByCreatedAtAfter(from);
        } else if (to != null) {
            return findByCreatedAtBefore(to);
        } else {
            return getAll();
        }
    }

    // 🧬 Restore Purity from previous snapshot

    @Override
    protected Purity undoLastMutation(Purity entity) {
        List<MutationHistory> history = entity.getMutationHistory();
        if (history == null || history.isEmpty()) {
            throw new IllegalStateException("No mutations to undo");
        }

        MutationHistory last = history.remove(history.size() - 1);
        Map<String, Object> stateMap = last.getPreviousState();

        Purity restored = new Purity();
        restored.setId((String) stateMap.get("id"));
        restored.setName((String) stateMap.get("name"));
        restored.setValue(Double.parseDouble(stateMap.get("value").toString()));

        String createdAtStr = (String) stateMap.get("createdAt");
        if (createdAtStr != null) {
            restored.setCreatedAt(LocalDateTime.parse(createdAtStr));
        }

        restored.setMutationHistory(history);
        Map<String, Object> snapshot = mapper.convertValue(entity, new TypeReference<Map<String, Object>>() {});
        history.add(new MutationHistory(Instant.now(), "UNDO", "system", snapshot));

        return restored;
    }
}