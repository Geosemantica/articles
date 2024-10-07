package com.geosemantica.articleservice.web.model.security;

import com.geosemantica.articleservice.facades.identities.AccountIdentity;

public interface TokenPrincipal {
    AccountIdentity getAccountIdentity();
}
