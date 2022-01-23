package com.parcel.ms.auth.service;

import com.parcel.ms.auth.exceptions.CacheException;
import com.parcel.ms.auth.model.SrpCacheData;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Log4j2
@Service
public class AuthnCacheService {
    @Autowired
    private RedissonClient redissonClient;

    private final String CACHE_PREFIX = "AUTH_SRP_SESSION";

    public SrpCacheData getSessionDataByLogin(String login) {
        RBucket<SrpCacheData> bucket = getBucket(login);
        if (!bucket.isExists()) {
            log.error("Bucket not exists");
            throw new CacheException("code", "Pre login cache is expired");
        }
        return bucket.get();
    }

    public void addSessionData(String login, SrpCacheData data) {
        RBucket<SrpCacheData> bucket = getBucket(login);
        log.debug("Adding data to bucket");
        bucket.set(data);
        bucket.expire(3L, TimeUnit.MINUTES);
    }

    public void deleteSessionDataByLogin(String login) {
        RBucket<SrpCacheData> bucket = getBucket(login);
        bucket.delete();
    }

    private RBucket<SrpCacheData> getBucket(String key)  {
        String bucketKey = String.format("%s:%s", CACHE_PREFIX, key);
        return redissonClient.getBucket(bucketKey);
    }
}
