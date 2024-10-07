package com.geosemantica.articleservice.web.model.security;

import com.geosemantica.articleservice.facades.model.enums.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@RequiredArgsConstructor
public class RoleAuthority implements GrantedAuthority {
    private final Role role;
    @Override
    public String getAuthority() {
        return role.name();
    }
}