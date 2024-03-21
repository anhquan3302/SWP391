package com.example.securityl.model.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {


    ADMIN_VIEW("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    STAFF_VIEW("staff:read"),
    STAFF_UPDATE("staff:update"),
    STAFF_CREATE("staff:create"),
    STAFF_DELETE("staff:delete"),

    DELIVERY_STAFF_VIEW("staff:read"),
    DELIVERY_STAFF_UPDATE("staff:update"),
    DELIVERY_STAFF_CREATE("staff:create"),
    DELIVERY_STAFF_DELETE("staff:delete"),

    USER_VIEW("user:read"),
    USER_UPDATE("user:update"),
    USER_CREATE("user:create"),
    USER_DELETE("user:delete");


    @Getter
    private final String perminssion;
}
