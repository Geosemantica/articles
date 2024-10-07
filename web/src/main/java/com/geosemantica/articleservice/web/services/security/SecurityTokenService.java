package com.geosemantica.articleservice.web.services.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.geosemantica.articleservice.facades.model.enums.Role;
import com.geosemantica.articleservice.web.model.security.RoleAuthority;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class SecurityTokenService {
    private static final String ROLE_ATTRIBUTE = "scp";
    private static final String ROLE_PREFIX = "ROLE_";
//    private final Algorithm algorithm;
//    private final SecurityProperties securityProperties;
//    private final TimeService timeService;
//    private final RefreshTokenServiceFacade refreshTokenService;

    public String getRoleClaimName() {
        return ROLE_ATTRIBUTE;
    }

    public String getRoleAuthorityPrefix() {
        return ROLE_PREFIX;
    }

    public Collection<RoleAuthority> readAuthorities(DecodedJWT jwt) {
        String role = jwt.getClaim(getRoleClaimName())
                .asString();
        Role enumRole = Role.valueOf(getRoleAuthorityPrefix() + role);

        return Collections.singleton(new RoleAuthority(enumRole));
    }

//    public OAuth2AccessToken createAccessTokenByPhone(@NonNull final String phone, @NonNull final Role role) {
//        Instant now = timeService.getCurrentInstant();
//        return createSigned(now, phone, toScope(role), securityProperties.getTemporaryTokenTTL());
//    }
//
//    public OAuth2AccessToken createAccessTokenByAccount(@NonNull final AccountFacade account) {
//        Instant now = timeService.getCurrentInstant();
//        DefaultOAuth2AccessToken accessToken = createSigned(now, String.valueOf(account.getId()), toScope(account.getRole()), securityProperties.getAccessTokenTTL());
//        return updateWithRefreshToken(now, account, accessToken);
//    }
//
//    public OAuth2AccessToken refreshAccessToken(@NonNull UUID refreshToken) throws RefreshTokenExpiredException {
//        Instant now = timeService.getCurrentInstant();
//        AccountFacade accountFacade = refreshTokenService.releaseToken(refreshToken, now);
//        if (accountFacade == null) {
//            throw new RefreshTokenExpiredException();
//        }
//        return createAccessTokenByAccount(accountFacade);
//    }
//
//    public OAuth2AccessToken updateWithRefreshToken(
//            @NonNull final Instant now,
//            @NonNull final AccountFacade account,
//            @NonNull final DefaultOAuth2AccessToken accessToken
//    ) {
//        Instant refreshTokenExpiration = now.plus(securityProperties.getRefreshTokenTTL());
//        UUID token;
//        try {
//            token = refreshTokenService.claimToken(account, now, refreshTokenExpiration);
//        }
//        catch (EntityNotFoundException e) {
//            throw new AuthenticationServiceException("wrong claimId for the specified token", e);
//        }
//        catch (RefreshTokenLimitExceededException e) {
//            throw new AuthenticationServiceException("too many token creating for the specified user", e);
//        }
//
//        DefaultOAuth2RefreshToken refreshToken = new DefaultOAuth2RefreshToken(token.toString());
//        accessToken.setRefreshToken(refreshToken);
//        return accessToken;
//    }
//
//    private DefaultOAuth2AccessToken createSigned(
//            final Instant now,
//            final String subject,
//            final String scope,
//            final Duration ttl) {
//        Instant accessTokenExpiration = now.plus(ttl);
//
//        String token = JWT.create()
//                .withClaim(ROLE_ATTRIBUTE, scope)
//                .withSubject(subject)
//                .withIssuer(securityProperties.getIssuer())
//                .withExpiresAt(Date.from(accessTokenExpiration))
//                .withIssuedAt(Date.from(now))
//                .sign(algorithm);
//        DefaultOAuth2AccessToken accessToken = new DefaultOAuth2AccessToken(token);
//        accessToken.setExpiration(new Date(accessTokenExpiration.toEpochMilli()));
//        accessToken.setScope(Collections.singleton(scope));
//        return accessToken;
//    }

//    private String toScope(Role role) {
//        String name = role.name();
//        if (!name.startsWith(getRoleAuthorityPrefix())) {
//            throw new IllegalArgumentException("role " + role + " is not supported because must be prefixed with " + getRoleAuthorityPrefix());
//        }
//
//        return name.substring(getRoleAuthorityPrefix().length());
//    }
}
