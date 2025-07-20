package com.example.metalpurity.service;

import com.example.metalpurity.common.HasMutationHistory;
import com.example.metalpurity.common.MutationHistory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BaseMutationService<E extends HasMutationHistory> {

    protected final ObjectMapper mapper;

    public BaseMutationService() {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
        this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // ISO 8601 format for LocalDateTime
    }

    protected void recordMutation(E entity, String type, String user) {
        List<MutationHistory> history = entity.getMutationHistory();
        if (history == null) history = new ArrayList<>();

        Map<String, Object> flattened = mapper.convertValue(
            entity, new TypeReference<Map<String, Object>>() {}
        );

        MutationHistory mutation = new MutationHistory(
            Instant.now(), type, user, flattened
        );

        history.add(mutation);
        entity.setMutationHistory(history);
    }

    protected E undoLastMutation(E entity) {
        List<MutationHistory> history = entity.getMutationHistory();
        if (history == null || history.isEmpty()) {
            throw new IllegalStateException("No mutations to undo");
        }

        MutationHistory last = history.remove(history.size() - 1);
        Map<String, Object> previousStateMap = last.getPreviousState();

        throw new UnsupportedOperationException(
            "Override this method in the subclass to reconstruct entity from snapshot map"
        );
    }
}