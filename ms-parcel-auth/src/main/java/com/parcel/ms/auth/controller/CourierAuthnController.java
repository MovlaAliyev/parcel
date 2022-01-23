package com.parcel.ms.auth.controller;

import com.parcel.ms.auth.model.*;
import com.parcel.ms.auth.service.CourierAuthService;
import com.parcel.ms.auth.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api("Authentication API")
@RequestMapping("/v1/auth/couriers")
class CourierAuthnController {

    private final CourierAuthService courierAuthService;

    @Autowired
    public CourierAuthnController(CourierAuthService courierAuthService) {
        this.courierAuthService = courierAuthService;
    }

    @ApiOperation("SRP step 1")
    @PostMapping("/srp/step1")
    public ResponseEntity<SrpFirstStepRespDto> srpAuthFirstStep(@RequestBody SrpStepFirstReqDto dto) {
        SrpFirstStepRespDto res = courierAuthService.srpAuthFirstStep(dto);
        return ResponseEntity.ok(res);
    }

    @ApiOperation("SRP step 2")
    @PostMapping("/srp/step2")
    public ResponseEntity<SrpSecondStepResDto> srpAuthStep2(@RequestBody SrpSecondStepReqDto dto) {
        SrpSecondStepResDto res = courierAuthService.srpAuthStep2(dto);
        return ResponseEntity.ok(res);
    }

}
