package com.geosemantica.articleservice.web.services.security;

import com.geosemantica.articleservice.web.model.security.TokenPrincipal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@RequiredArgsConstructor
@Getter
public class AccountIdentityAuthentication implements Authentication {
    private final Collection<? extends GrantedAuthority> authorities;
    private final TokenPrincipal principal;

    @Override
    public Object getCredentials() {
        return getPrincipal().getAccountIdentity();
    }

    @Override
    public Object getDetails() {
        return getPrincipal().getAccountIdentity();
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Impossible to change auth.");
    }

    @Override
    public String getName() {
        return String.valueOf(principal.getAccountIdentity().getId());
    }
}
