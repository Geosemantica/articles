package com.geosemantica.articleservice.web.model.security;

import com.geosemantica.articleservice.facades.model.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.ConfigAttribute;

@RequiredArgsConstructor
public class RoleConfigAttribute implements ConfigAttribute {
    private final Role role;
    @Override
    public String getAttribute() {
        return role.name();
    }
}
