package com.parcel.ms.auth.controller;

import com.parcel.ms.auth.model.*;
import com.parcel.ms.auth.service.UserAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api("Authentication API")
@RequestMapping("/v1/auth/users")
class UserAuthnController {

    private final UserAuthService userAuthService;

    @Autowired
    public UserAuthnController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @ApiOperation("SRP step 1")
    @PostMapping("/srp/step1")
    public ResponseEntity<SrpFirstStepRespDto> srpAuthFirstStep(@RequestBody SrpStepFirstReqDto dto) {
        SrpFirstStepRespDto res = userAuthService.srpAuthFirstStep(dto);
        return ResponseEntity.ok(res);
    }

    @ApiOperation("SRP step 2")
    @PostMapping("/srp/step2")
    public ResponseEntity<SrpSecondStepResDto> srpAuthStep2(@RequestBody SrpSecondStepReqDto dto) {
        SrpSecondStepResDto res = userAuthService.srpAuthStep2(dto);
        return ResponseEntity.ok(res);
    }
}
