package com.example.metalpurity.common;

import java.time.Instant;
import java.util.Map;

public class MutationHistory {
    private Instant timestamp;
    private String changeType;
    private String changedBy;
    private Map<String, Object> previousState;

    public MutationHistory() {}

    public MutationHistory(Instant timestamp, String changeType, String changedBy, Map<String, Object> previousState) {
        this.timestamp = timestamp;
        this.changeType = changeType;
        this.changedBy = changedBy;
        this.previousState = previousState;
    }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

    public String getChangeType() { return changeType; }
    public void setChangeType(String changeType) { this.changeType = changeType; }

    public String getChangedBy() { return changedBy; }
    public void setChangedBy(String changedBy) { this.changedBy = changedBy; }

    public Map<String, Object> getPreviousState() { return previousState; }
    public void setPreviousState(Map<String, Object> previousState) { this.previousState = previousState; }
}