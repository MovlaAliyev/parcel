package com.parcel.ms.reg.service;

import com.parcel.ms.reg.client.otp.OtpClient;
import com.parcel.ms.reg.client.otp.model.OtpCreateClientRequest;
import com.parcel.ms.reg.client.otp.model.OtpCreateClientResponse;
import com.parcel.ms.reg.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

    private final String subject;
    private final String message;

    private final OtpClient otpClient;


    private final OtpCacheService otpCacheService;
    private final RegistrationSessionService registrationSessionService;

    @Autowired
    public OtpService(
            @Value("${otp.message}") String message,
            @Value("${otp.subject}") String subject,
            OtpClient otpClient,
            OtpCacheService otpCacheService,
            RegistrationSessionService registrationSessionService) {

        this.message = message;
        this.subject = subject;

        this.otpClient = otpClient;
        this.otpCacheService = otpCacheService;
        this.registrationSessionService = registrationSessionService;
    }

    public OtpCreateResponse createOtp(OtpCreateRequest dto){
        String sessionKey = registrationSessionService.createSession(dto.getEmail());

        OtpCreateClientResponse otp = otpClient.createOtp(new OtpCreateClientRequest(dto.getEmail(), subject, message));

        otpCacheService.addSessionData(sessionKey, otp.getUuid());

        return new OtpCreateResponse(otp.getResendDelay(), sessionKey, otp.getExpirationTime());
    }

    public String checkOtp(OtpCheckRequest dto) {
        String otpUuid = otpCacheService.getOtpUuidBySessionKey(dto.getSessionKey());

        String email = registrationSessionService.getSessionData(dto.getSessionKey());

        otpClient.checkOtp(new OtpCheckClientRequest(otpUuid, dto.getOtpCode()));

        otpCacheService.deleteSessionDataBySessionKey(dto.getSessionKey());

        return email;
    }



}
