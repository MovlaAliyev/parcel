package com.parcel.ms.auth.service;

import com.nimbusds.srp6.BigIntegerUtils;
import com.nimbusds.srp6.SRP6Exception;
import com.parcel.ms.auth.client.user.UserClient;
import com.parcel.ms.auth.exceptions.AuthException;
import com.parcel.ms.auth.helper.SR6Helper;
import com.parcel.ms.auth.model.*;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Log4j2
@Service
public class AuthnService {

    private final UserClient userClient;

    private final SR6Helper srpHelper;
    private final TokenService tokenService;
    private final AuthnCacheService authnCacheService;

    @Autowired
    public AuthnService(
            SR6Helper srpHelper,
            UserClient userClient,
            TokenService tokenService,
            AuthnCacheService authnCacheService) {
        this.srpHelper = srpHelper;
        this.userClient = userClient;
        this.tokenService = tokenService;
        this.authnCacheService = authnCacheService;
    }

    public SrpFirstStepRespDto srpAuthFirstStep(User user, SrpStepFirstReqDto dto) {
        val srpSession = srpHelper.generateSRP6ServerSession();

        BigInteger srp1B;
        BigInteger salt;

        val srpCacheData = new SrpCacheData();
        srpCacheData.setSrp6ServerSession(srpSession);

        try {

            log.debug("Srp step first for userId {}", user.getId());

            srpCacheData.setUser(user);

            val v = BigIntegerUtils.fromHex(user.getVerifier());
            salt = BigIntegerUtils.fromHex(user.getSalt());

            srp1B = srpSession.step1(dto.getLogin(), salt, v);
        } catch (AuthException ex) {
            log.warn("Failed to do real SRP1", ex);
            salt = srpHelper.generateRandomSalt();
            srp1B = srpSession.mockStep1(dto.getLogin(), salt, BigInteger.ZERO);
        }

        authnCacheService.addSessionData(dto.getLogin(), srpCacheData);

        return new SrpFirstStepRespDto(
                BigIntegerUtils.toHex(srp1B),
                BigIntegerUtils.toHex(salt)
        );
    }

    public SrpSecondStepResDto srpAuthStep2(SrpSecondStepReqDto dto) {
        val cacheData = authnCacheService.getSessionDataByLogin(dto.getLogin());

        val a = BigIntegerUtils.fromHex(dto.getSrpA());
        val m1 = BigIntegerUtils.fromHex(dto.getSrpM1());

        val srpSession = cacheData.getSrp6ServerSession();

        BigInteger spr2M2;
        try {
            spr2M2 = srpSession.step2(a, m1);
        } catch (SRP6Exception e) {
            log.debug("Invalid password");
            throw new AuthException("error.auth.invalidCredentials", "Invalid credentials");
        }

        val tokens = tokenService.generateTokens(cacheData.getUser().getId(), 0);

        authnCacheService.deleteSessionDataByLogin(dto.getLogin());

        return new SrpSecondStepResDto(
                BigIntegerUtils.toHex(spr2M2),
                tokens.getAccessToken(),
                tokens.getRefreshToken()

        );
    }

}
