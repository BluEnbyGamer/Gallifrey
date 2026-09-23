package com.timelordmod.gallifrey.sonic;

public enum SonicMode {
    INTERACTION("Interaction"),
    OVERLOAD("Overload"),
    SCAN("Scanning");

    private final String displayName;

    SonicMode(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
