package com.aulkhami.pakupos.app.enums;

public enum EntityStatus {
    ACTIVE,
    INACTIVE,
    SUSPENDED,
    DELETED;

    public boolean isActive() {
        return this == ACTIVE;
    }
}
