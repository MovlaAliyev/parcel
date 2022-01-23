package com.parcel.ms.reg.service;

import com.parcel.ms.reg.exceptions.OtpCacheException;
import com.parcel.ms.reg.model.LoginOtpCache;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Log4j2
@Service
public class OtpCacheService {

    @Autowired
    private RedissonClient redissonClient;

    private final String CACHE_PREFIX = "PRE_AUTH_SESSION";

    public String getOtpUuidBySessionKey(String sessionKey) {
        RBucket<LoginOtpCache> bucket = getBucket(sessionKey);
        if (!bucket.isExists()) {
            log.error("Bucket not exists");
            throw new OtpCacheException("error.OtpExpiredCache", "Pre login cache is expired");
        }
        return bucket.get().getOtpUUID();
    }

    public void addSessionData(String sessionKey, String otpUUID) {
        RBucket<LoginOtpCache> bucket = getBucket(sessionKey);
        log.debug("Adding data to bucket");
        bucket.set(new LoginOtpCache(sessionKey, otpUUID));
        bucket.expire(3L, TimeUnit.MINUTES);
    }

    public void deleteSessionDataBySessionKey(String sessionKey) {
        RBucket<LoginOtpCache> bucket = getBucket(sessionKey);
        log.debug("Deleting bucket");
        bucket.delete();
    }

    private RBucket<LoginOtpCache> getBucket(String key)  {
        String bucketKey = String.format("%s:%s", CACHE_PREFIX, key);
        log.debug("Getting bucket: $bucketKey");
        return redissonClient.getBucket(bucketKey);
    }
}
