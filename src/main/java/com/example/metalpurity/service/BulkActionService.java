package com.example.metalpurity.service;

import java.util.List;
import java.util.function.Function;
import org.springframework.stereotype.Service;

@Service
public class BulkActionService {
    public <T> List<T> apply(List<String> ids, Function<String, T> processor) {
        return ids.stream().map(processor).toList();
    }
}