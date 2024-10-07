package com.geosemantica.articleservice.web.services.security;

import com.geosemantica.articleservice.facades.model.enums.Role;
import com.geosemantica.articleservice.web.model.security.AnonymousPrincipal;
import com.geosemantica.articleservice.web.model.security.RoleAuthority;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class AnonymousAuthentication implements Authentication {
    private final AnonymousPrincipal principal;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new RoleAuthority(Role.ROLE_ANONYMOUS));
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getDetails() {
        return "anonymous";
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Impossible to change auth.");
    }

    @Override
    public String getName() {return "anonymous";}
}