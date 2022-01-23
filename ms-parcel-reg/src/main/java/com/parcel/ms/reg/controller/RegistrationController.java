package com.parcel.ms.reg.controller;

import com.parcel.ms.reg.model.*;
import com.parcel.ms.reg.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/sign-up/users")
public class RegistrationController {

    private final RegistrationService service;

    @Autowired
    public RegistrationController(RegistrationService service) {
        this.service = service;
    }

    @PostMapping("/otp/create")
    public OtpCreateResponse createOtp(@RequestBody OtpCreateRequest dto) {
        return service.createOtp(dto);
    }

    @PostMapping("/otp/check")
    public void checkOtp(@RequestBody OtpCheckRequest dto) {
        service.checkOtp(dto);
    }

    @PostMapping("/user/save")
    public void saveUserInfo(@RequestBody UserInfoRequest dto) {
        service.saveUserInfo(dto);
    }

    @PostMapping("/user/password")
    public void saveUserPassword(@RequestBody PasswordRequest dto) {
        service.savePassword(dto);
    }
}
