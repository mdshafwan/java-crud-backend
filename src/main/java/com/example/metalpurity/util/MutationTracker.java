package com.example.metalpurity.util;

import java.time.Instant;
import java.util.Map;

import com.example.metalpurity.common.MutationHistory;

public class MutationTracker {
    public static MutationHistory create(String type, String user, Map<String, Object> snapshot) {
        return new MutationHistory(Instant.now(), type, user, snapshot);
    }

    public static <T> Map<String, Object> flatten(T entity) {
        // Plug in your Jackson, Gson, or ModelMapper logic here
        // Example using Jackson (if ObjectMapper is injected here or reused)
        // ObjectMapper mapper = new ObjectMapper();
        // return mapper.convertValue(entity, new TypeReference<Map<String, Object>>() {});
        return null; // placeholder for actual logic
    }
}