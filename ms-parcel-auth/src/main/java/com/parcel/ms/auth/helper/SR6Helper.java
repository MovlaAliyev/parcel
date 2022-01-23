package com.parcel.ms.auth.helper;

import com.nimbusds.srp6.*;
import com.parcel.ms.auth.crypto.SRP6ThinbusRoutines;
import lombok.val;
import org.jboss.marshalling.Pair;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class SR6Helper {

    private SRP6CryptoParams CRYPTO_PARAMS;

    public SR6Helper() {
        this.CRYPTO_PARAMS = new SRP6CryptoParams(SRP6CryptoParams.N_256, SRP6CryptoParams.g_common, "SHA-256");
    }

    public SRP6ClientSession generateSRP6ClientSession() {
        val srp6ClientSession = new SRP6ClientSession();
        srp6ClientSession.setXRoutine(SRP6ThinbusRoutines.getXRoutine());
        srp6ClientSession.setHashedKeysRoutine( SRP6ThinbusRoutines.getURoutine());
        srp6ClientSession.setClientEvidenceRoutine(SRP6ThinbusRoutines.getClientEvidenceRoutine());
        srp6ClientSession.setServerEvidenceRoutine(SRP6ThinbusRoutines.getServerEvidenceRoutine());
        return srp6ClientSession;
    }

    public SRP6ServerSession generateSRP6ServerSession() {
        val srp6ServerSession = new SRP6ServerSession(CRYPTO_PARAMS);
        srp6ServerSession.setClientEvidenceRoutine(SRP6ThinbusRoutines.getClientEvidenceRoutine());
        srp6ServerSession.setHashedKeysRoutine(SRP6ThinbusRoutines.getURoutine());
        srp6ServerSession.setServerEvidenceRoutine(SRP6ThinbusRoutines.getServerEvidenceRoutine());
        return srp6ServerSession;
    }

    public SRP6VerifierGenerator generateSRP6VerifierGenerator() {
        val srp6VerifierGenerator = new SRP6VerifierGenerator(CRYPTO_PARAMS);
        srp6VerifierGenerator.setXRoutine(SRP6ThinbusRoutines.getXRoutine());
        return srp6VerifierGenerator;
    }

    public BigInteger generateRandomSalt() {
        val gen = generateSRP6VerifierGenerator();
        return new BigInteger(1, gen.generateRandomSalt());
    }

    public Pair<String, String> generateSaltAndVerifierForUser(String login, String password) {
        val gen = generateSRP6VerifierGenerator();
        val salt = new BigInteger(1, gen.generateRandomSalt());
        val verifier = gen.generateVerifier(salt, login, password);
        return new Pair(
                BigIntegerUtils.toHex(salt),
                BigIntegerUtils.toHex(verifier)
        );
    }
}
