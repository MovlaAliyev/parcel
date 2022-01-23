package com.parcel.ms.reg.service;

import com.parcel.ms.reg.exceptions.RegistrationCacheException;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Log4j2
@Service
public class RegistrationSessionService {

    @Autowired
    private RedissonClient redissonClient;

    private final String CACHE_PREFIX = "PARCEL_REGISTRATION_SESSION";

    public String createSession(String email){
        String sessionKey = UUID.randomUUID().toString();
        RBucket<String> bucket = getBucket(sessionKey);
        log.debug("Adding registration session data");
        long EXPIRATION_TIME = 5L;
        bucket.set(email, EXPIRATION_TIME, TimeUnit.MINUTES);
        return sessionKey;
    }

    public String getSessionData(String sessionKey) {
        RBucket<String> bucket = getBucket(sessionKey);
        if (!bucket.isExists()) {
            log.error("Bucket not exists");
            throw new RegistrationCacheException(
                    "error.RegistrationCacheExpired",
                    "Registration session expired start from otp"
            );
        }
        return getBucket(sessionKey).get();
    }

    public void deleteRegistrationSession(String sessionKey) {
        RBucket<String> bucket = getBucket(sessionKey);
        log.debug("Deleting session");
        bucket.delete();
    }

    private RBucket<String> getBucket(String key) {
        String bucketKey = String.format("%s:%s", CACHE_PREFIX, key);
        log.debug("Getting bucket: $bucketKey");
        return redissonClient.getBucket(bucketKey);
    }
}
