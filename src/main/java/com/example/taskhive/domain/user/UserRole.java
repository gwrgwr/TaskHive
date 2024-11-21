package com.example.taskhive.domain.user;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("admin"),
    STAFF("staff"),
    USER("user"),
    GUEST("guest");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

}
