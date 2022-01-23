package com.parcel.ms.auth.service;

import com.google.gson.Gson;
import com.parcel.ms.auth.exceptions.AuthException;
import com.parcel.ms.auth.model.SessionCacheData;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Log4j2
@Service
public class SessionCacheService {

    private final Gson gson;
    private final RedissonClient redissonClient;

    @Autowired
    public SessionCacheService(Gson gson, RedissonClient redissonClient) {
        this.gson = gson;
        this.redissonClient = redissonClient;
    }

    private final String CACHE_PREFIX = "AUTH_SESSION";

    public void putCacheData(long userId, SessionCacheData data, long expirationSecs) {
        RBucket<String> bucket = getCacheBucket(userId);
        bucket.set(gson.toJson(data));
        bucket.expire(expirationSecs, TimeUnit.SECONDS);
    }

    private RBucket<String> getCacheBucket(long userId) {
        String bucketKey = CACHE_PREFIX + ":" + userId;
        return redissonClient.getBucket(bucketKey);
    }

    public SessionCacheData getCacheData(long userId) {
        RBucket<String> bucket = getCacheBucket(userId);
        if (!bucket.isExists()) {
            throw new AuthException("error.session.expired", "Session is expired");
        }
        return gson.fromJson(bucket.get(), SessionCacheData.class);
    }
}
