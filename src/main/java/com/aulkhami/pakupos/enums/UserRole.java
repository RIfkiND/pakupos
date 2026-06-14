package com.aulkhami.pakupos.enums;

public enum UserRole {
    OWNER("OWNER", "Owner"),
    KARYAWAN("KARYAWAN", "Karyawan");

    private final String code;
    private final String label;

    UserRole(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public static UserRole fromCode(String code) {
        if (code == null || code.isBlank()) {
            return KARYAWAN; // Default
        }
        for (UserRole role : values()) {
            if (role.code.equalsIgnoreCase(code.trim())) {
                return role;
            }
        }
        return KARYAWAN;
    }
}
