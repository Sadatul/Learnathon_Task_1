package com.district12.backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    USER("SCOPE_USER"),
    ADMIN("SCOPE_ADMIN");

    private final String value;
}
