package com.geosemantica.articleservice.facades.model.enums;

import java.util.EnumSet;
import java.util.Set;

public enum Role {
    // Rename only with corresponding below values
    ROLE_ANONYMOUS,
    ROLE_PHONE_VERIFIED,
    ROLE_ADMIN,
    ROLE_USER,
    ROLE_MODERATOR;

    // These values are hold in auth token. If you change them, token become invalid
    public static final String ANONYMOUS = "ROLE_ANONYMOUS";
    public static final String PHONE_VERIFIED = "ROLE_PHONE_VERIFIED";
    public static final String ADMIN = "ROLE_ADMIN";
    public static final String DEVELOPER = "ROLE_DEVELOPER";
    public static final String MODERATOR = "ROLE_MODERATOR";

    public static final Set<Role> USER_ROLES = EnumSet.of(ROLE_ADMIN, ROLE_USER, ROLE_MODERATOR);
}
