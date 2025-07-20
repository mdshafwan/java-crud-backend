package com.example.metalpurity.common;

import java.util.List;

public interface HasMutationHistory {
    List<MutationHistory> getMutationHistory();
    void setMutationHistory(List<MutationHistory> history);
}