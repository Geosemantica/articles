package com.geosemantica.articleservice.web.services.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.geosemantica.articleservice.facades.identities.AccountIdentity;
import com.geosemantica.articleservice.facades.model.enums.Role;
import com.geosemantica.articleservice.web.model.security.RoleAuthority;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.oauth2.server.resource.BearerTokenErrorCodes;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Slf4j
public class BearerJwtAuthenticationProvider implements AuthenticationProvider {
//    private final JWTVerifier jwtVerifier;
    private final SecurityTokenService securityTokenService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        BearerTokenAuthenticationToken bearer = (BearerTokenAuthenticationToken) authentication;

        DecodedJWT decodedJWT;
        // With a microservice approach, we usually do not verify the authorization token,
        // but only decode it, since verification is handled by the gateway/authorization service.
//        try {
//            decodedJWT = jwtVerifier.verify(bearer.getToken());
//        }
//        catch (Exception e) {
//            throw new OAuth2AuthenticationException(invalidToken("JWT verification failed."), e);
//        }
        try {
            decodedJWT = JWT.decode(bearer.getToken());
        }
        catch (Exception e) {
            throw new OAuth2AuthenticationException(invalidToken("JWT verification failed."), e);
        }

        Collection<RoleAuthority> authorities;
        try {
            authorities = securityTokenService.readAuthorities(decodedJWT);
        }
        catch (Exception e) {
            throw new OAuth2AuthenticationException(
                    new BearerTokenError(
                            BearerTokenErrorCodes.INSUFFICIENT_SCOPE,
                            HttpStatus.FORBIDDEN,
                            "JWT has wrong scope",
                            "https://tools.ietf.org/html/rfc6750#section-3.1"),
                    e
            );
        }

        String subject;
        try {
            subject = decodedJWT.getSubject();
            if (subject == null) {
                throw new NullPointerException("Subject must not be null.");
            }
        }
        catch (Exception e) {
            throw new OAuth2AuthenticationException(invalidToken("Wrong subject value."), e);
        }

        Role singleRole = authorities.size() == 1 ? authorities.stream().findFirst().get().getRole() : null;
        if (singleRole != null && Role.USER_ROLES.contains(singleRole)) {
            try {
                final UUID subjectId = UUID.fromString(subject);
                final AccountIdentity accountIdentity = () -> subjectId;
                return new AccountIdentityAuthentication(authorities, () -> accountIdentity);
            }
            catch (Exception e) {
                log.debug("Subject is in a wrong format.", e);
            }
        }
        Map<String, Object> claims;
        try {
            claims = decodedJWT.getClaims()
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, kv -> kv.getValue().as(Object.class)));
        }
        catch (Exception e) {
            throw new OAuth2AuthenticationException(invalidToken("Wrong JWT claims format."), e);
        }
        return new JwtAuthentication(authorities, subject, claims);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return BearerTokenAuthenticationToken.class.isAssignableFrom(authentication);
    }


    private static OAuth2Error invalidToken(String message) {
        return new BearerTokenError(
                BearerTokenErrorCodes.INVALID_TOKEN,
                HttpStatus.UNAUTHORIZED,
                message,
                "https://tools.ietf.org/html/rfc6750#section-3.1");
    }
}
