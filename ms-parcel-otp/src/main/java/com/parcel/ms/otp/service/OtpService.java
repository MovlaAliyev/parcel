package com.parcel.ms.otp.service;

import com.parcel.ms.otp.enums.OtpStatus;
import com.parcel.ms.otp.exceptions.OtpAttemptException;
import com.parcel.ms.otp.exceptions.OtpException;
import com.parcel.ms.otp.model.*;
import com.parcel.ms.otp.queue.QueueProducer;
import org.apache.commons.lang3.RandomStringUtils;
import org.redisson.api.RBucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OtpService {

    private final int delay;
    private final int length;
    private final int totalAttempts;
    private final int expirationTime;

    private QueueProducer queueProducer;
    private final OtpCacheSessionService otpCacheSessionService;

    @Autowired
    public OtpService(
            @Value("${otp.delay}") int delay,
            @Value("${otp.length}") int length,
            @Value("${otp.total-attempts}") int totalAttempts,
            @Value("${otp.expiration-time}") int expirationTime,
            QueueProducer queueProducer,
            OtpCacheSessionService otpCacheSessionService) {
        this.delay = delay;
        this.length = length;
        this.totalAttempts = totalAttempts;
        this.expirationTime = expirationTime;

        this.queueProducer = queueProducer;
        this.otpCacheSessionService = otpCacheSessionService;
    }

    public OtpCreateResponse createOtp(OtpCreateRequest dto){
        String otpKey = UUID.randomUUID().toString();
        String code = RandomStringUtils.random(length, false, true);

        OtpInfo info = new OtpInfo(code, OtpStatus.CREATED, totalAttempts);

        otpCacheSessionService.addSessionData(info, otpKey, expirationTime);

        queueProducer.sendToQueue(
                new MailInfo(dto.getEmail(), dto.getSubject(),
                        dto.getMessageTemplate().replace("{otp_code}", code))
        );

        return new OtpCreateResponse(code, otpKey, delay, expirationTime);

    }

    public void checkOtp(OtpCheckRequest dto) {
        RBucket<OtpInfo> otp = otpCacheSessionService.getOtpUuidBySessionKey(dto.getUuid());

        OtpInfo info = otp.get();

        info.setAttemptsLeft(info.getAttemptsLeft() - 1);

        if(!info.getCode().equals(dto.getOtpCode())){
            if(info.getAttemptsLeft() <= 0){
                throw new OtpAttemptException("error.noAttempt", "Invalid otp. No attempts left");
            }else {
                throw new OtpException("error.invalidOtp", "Invalid otp. Attempts left: " + info.getAttemptsLeft());
            }
        }else {
            info.setStatus(OtpStatus.APPROVED);
        }

        otpCacheSessionService.deleteSessionDataByOtpKey(dto.getUuid());
    }

}
