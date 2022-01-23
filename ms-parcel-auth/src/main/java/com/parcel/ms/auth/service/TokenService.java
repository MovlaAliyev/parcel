package com.parcel.ms.auth.service;

import com.parcel.ms.auth.exceptions.AuthException;
import com.parcel.ms.auth.model.AccessTokenClaimsSet;
import com.parcel.ms.auth.model.RefreshTokenClaimsSet;
import com.parcel.ms.auth.model.SessionCacheData;
import com.parcel.ms.auth.model.TokensDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.security.KeyPair;
import java.time.LocalDateTime;

@Log4j2
@Service
public class TokenService {

    private final JwtService jwtService;
    private final SessionCacheService sessionCacheService;

    @Value("${jwt.accessToken.expirationTime}")
    private long accessTokenExpirationTimeSecs;

    @Value("${jwt.refreshToken.expirationTime}")
    private long refreshTokenExpirationTimeSecs;

    @Value("${jwt.refreshToken.expirationCount}")
    private int refreshTokenExpirationCount;

    @Autowired
    public TokenService(JwtService jwtService, SessionCacheService sessionCacheService) {
        this.jwtService = jwtService;
        this.sessionCacheService = sessionCacheService;
    }

    public TokensDto generateTokens(
            long userId,
            int count
    ) {
        AccessTokenClaimsSet accessTokenClaimsSet = new AccessTokenClaimsSet();
        accessTokenClaimsSet.setUserId(userId);
        accessTokenClaimsSet.setExpirationTimeInSeconds(accessTokenExpirationTimeSecs);

        RefreshTokenClaimsSet refreshTokenClaimsSet = new RefreshTokenClaimsSet();
        refreshTokenClaimsSet.setUserId(userId);
        refreshTokenClaimsSet.setCount((count == 0)? refreshTokenExpirationCount : count);
        refreshTokenClaimsSet.setExpirationTimeInSeconds(refreshTokenExpirationTimeSecs);

        KeyPair keyPair = jwtService.generateKeyPair();

        String accessToken = jwtService.generateToken(accessTokenClaimsSet, keyPair.getPrivate());
        String refreshToken = jwtService.generateToken(refreshTokenClaimsSet, keyPair.getPrivate());

        SessionCacheData sessionData = new SessionCacheData(
                Base64Utils.encodeToString(keyPair.getPublic().getEncoded()),
                accessTokenClaimsSet);

        sessionCacheService.putCacheData(
                userId, sessionData,
                refreshTokenExpirationTimeSecs
        );


        return new TokensDto(accessToken, refreshToken);
    }

    public TokensDto refreshTokens(String refreshToken) {
        RefreshTokenClaimsSet claimsSet = jwtService.getClaimsFromRefreshToken(refreshToken);

        SessionCacheData cacheData = sessionCacheService.getCacheData(claimsSet.getUserId());

        jwtService.verifyToken(refreshToken, cacheData.getPublicKey());

        if (claimsSet.getExpiresAt().isBefore(LocalDateTime.now())) {
            log.debug("Refresh token time has been expired");
            throw new AuthException("error.refreshToken.expired", "Refresh token has been expired");
        }

        if (claimsSet.getCount() <= 0) {
            throw new AuthException("error.refreshToken.expired", "Refresh token has been expired");
        }

        return generateTokens(claimsSet.getUserId(), claimsSet.getCount() - 1);
    }

    public long verifyAccessToken(String accessToken) {
        if (accessToken == null) {
            log.warn("Access token is missing");
            throw new AuthException("error.validateAccessToken.expired", "Access token is missing");
        }

        AccessTokenClaimsSet claimsSet = jwtService.getClaimsFromAccessToken(accessToken);
        log.debug("Verifying token for user: {}", claimsSet.getUserId());
        validateExpiration(claimsSet);
        SessionCacheData cacheData = sessionCacheService.getCacheData(claimsSet.getUserId());

        jwtService.verifyToken(accessToken, cacheData.getPublicKey());

        return claimsSet.getUserId();
    }

    private void validateExpiration(AccessTokenClaimsSet accessTokenClaimsSet) {
        if (accessTokenClaimsSet.getExpiresAt().isBefore(LocalDateTime.now())) {
           throw new AuthException("error.validateAccessToken.expired", "Access token expired");
        }
    }
}
