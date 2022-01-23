package com.parcel.ms.otp.service;

import com.parcel.ms.otp.exceptions.OtpCacheException;
import com.parcel.ms.otp.model.OtpInfo;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class OtpCacheSessionService {

    private final RedissonClient redissonClient;

    @Autowired
    public OtpCacheSessionService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    private final String CACHE_PREFIX = "OTP";

    public RBucket<OtpInfo> getOtpUuidBySessionKey(String otpKey) {
        RBucket<OtpInfo> bucket = getBucket(otpKey);
        if (!bucket.isExists()) {
            throw new OtpCacheException("code", "Otp expired");
        }
        return bucket;
    }

    public void addSessionData(OtpInfo info, String key, int expirationTime) {
        RBucket<OtpInfo> bucket = getBucket(key);
        bucket.set(info);
        bucket.expire(expirationTime, TimeUnit.MINUTES);
    }

    public void deleteSessionDataByOtpKey(String otpKey) {
        RBucket<OtpInfo> bucket = getBucket(otpKey);
        bucket.delete();
    }

    private RBucket<OtpInfo> getBucket(String key)  {
        String bucketKey = String.format("%s:%s", CACHE_PREFIX, key);
        return redissonClient.getBucket(bucketKey);
    }
}
