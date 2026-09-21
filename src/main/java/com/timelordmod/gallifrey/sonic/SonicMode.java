package com.timelordmod.gallifrey.sonic;

public enum SonicMode {

    SCAN("Scan"),
    ACTIVATE("Activate");

    private final String displayName;

    SonicMode(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}