package com.geosemantica.articleservice.web.model.security;

import com.geosemantica.articleservice.facades.identities.AccountIdentity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;

@RequiredArgsConstructor
public class AnonymousPrincipal implements TokenPrincipal {
    @Override
    public AccountIdentity getAccountIdentity() {
        throw new AccessDeniedException("Anonymous user has not identity.");
    }
}
