package dev.danvega.dev2next;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SessionType {
    WORKSHOP("Workshop"),
    KEYNOTE("Keynote"),
    TALK("Talk"),
    GENERAL_SESSION("General Session");

    private final String displayName;

    SessionType(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static SessionType fromString(String value) {
        for (SessionType type : SessionType.values()) {
            if (type.displayName.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown session type: " + value);
    }

    @Override
    public String toString() {
        return displayName;
    }
}