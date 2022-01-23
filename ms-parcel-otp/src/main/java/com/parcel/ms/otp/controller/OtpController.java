package com.parcel.ms.otp.controller;

import com.parcel.ms.otp.model.OtpCheckRequest;
import com.parcel.ms.otp.model.OtpCreateRequest;
import com.parcel.ms.otp.model.OtpCreateResponse;
import com.parcel.ms.otp.service.OtpService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/v1/internal/otp")
@ApiOperation(value = "Otp Controller")
public class OtpController {

    private final OtpService service;

    @Autowired
    public OtpController(OtpService service) {
        this.service = service;
    }


    @ApiOperation("create otp and send mail")
    @PostMapping(value = "/create")
    public OtpCreateResponse createOtp(
            @RequestBody @Valid @NotNull OtpCreateRequest dto) {
        return service.createOtp(dto);
    }

    @ApiOperation("check otp")
    @PostMapping(value = "/check")
    public void checkOtp(
            @RequestBody @Valid @NotNull OtpCheckRequest dto) {
        service.checkOtp(dto);
    }

}
