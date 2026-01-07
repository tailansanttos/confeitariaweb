package com.tailan.confeitaria.web.domain.enums;

public enum UserRole {
    ADMIN("admin"),
    USER("user");
    private String value;
    UserRole(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
