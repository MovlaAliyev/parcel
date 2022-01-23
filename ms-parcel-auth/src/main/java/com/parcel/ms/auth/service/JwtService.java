package com.parcel.ms.auth.service;

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.parcel.ms.auth.exceptions.AuthException;
import com.parcel.ms.auth.model.AccessTokenClaimsSet;
import com.parcel.ms.auth.model.RefreshTokenClaimsSet;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class JwtService {

    private final Gson gson;

    private final int KEY_SIZE = 2048;
    private final String ALGORITHM = "RSA";

    @Autowired
    public JwtService(Gson gson) {
        this.gson = gson;
    }

    @SneakyThrows
    public KeyPair generateKeyPair() {
        KeyPairGenerator gen = KeyPairGenerator.getInstance(ALGORITHM);
        gen.initialize(KEY_SIZE);
        return gen.generateKeyPair();
    }

    public String generateToken(AccessTokenClaimsSet claimsSet, PrivateKey privateKey) {
        claimsSet.setCreatedAt(LocalDateTime.now());
        claimsSet.setExpiresAt(LocalDateTime.now().plusSeconds(claimsSet.getExpirationTimeInSeconds()));
        return generateSignedJWT(gson.toJson(claimsSet), privateKey).serialize();
    }

    public String generateToken(RefreshTokenClaimsSet claimsSet, PrivateKey privateKey) {
        claimsSet.setExpiresAt(LocalDateTime.now().plusSeconds(claimsSet.getExpirationTimeInSeconds()));
        return generateSignedJWT(gson.toJson(claimsSet), privateKey).serialize();
    }

    @SneakyThrows
    private SignedJWT generateSignedJWT(String tokenClaimSetJson, PrivateKey privateKey) {
        val jwtClaimsSet  = JWTClaimsSet.parse(tokenClaimSetJson);
        val header = new JWSHeader(JWSAlgorithm.RS256);
        val signedJWT = new SignedJWT(header, jwtClaimsSet);
        val signer = new RSASSASigner(privateKey);
        signedJWT.sign(signer);
        return signedJWT;
    }

    public RefreshTokenClaimsSet getClaimsFromRefreshToken(String token) {
        return gson.fromJson(getClaimsFromToken(token).toString(), RefreshTokenClaimsSet.class);
    }

    public AccessTokenClaimsSet getClaimsFromAccessToken(String token) {
        return gson.fromJson(getClaimsFromToken(token).toString(), AccessTokenClaimsSet.class);
    }

    @SneakyThrows
    private JWTClaimsSet getClaimsFromToken(String token) {
        return SignedJWT.parse(token).getJWTClaimsSet();
    }

    @SneakyThrows
    public void verifyToken(String token, String publicKey) {
        val key = KeyFactory.getInstance("RSA").generatePublic(
                new X509EncodedKeySpec(Base64Utils.decodeFromString(publicKey)));

        val signedJwt = SignedJWT.parse(token);
        val verifier = new RSASSAVerifier((RSAPublicKey)key);
        if (!signedJwt.verify(verifier)) {
            throw new AuthException("error.jwtSignature.invalid", "JWT signature verification failed");
        }
    }
}
