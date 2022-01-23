package com.parcel.ms.auth.controller;

import com.parcel.ms.auth.model.TokensDto;
import com.parcel.ms.auth.service.TokenService;
import io.swagger.annotations.ApiOperation;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class TokenController {

    private final TokenService tokenService;

    @Autowired
    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/token/verify")
    public ResponseEntity<String> verifyToken(
            @RequestHeader(value = "dp-access-token", required = false) String accessToken
    ) {
        long verifiedUserId = tokenService.verifyAccessToken(accessToken);
        return ResponseEntity.ok()
                .header("parcel-user-id", String.valueOf(verifiedUserId))
                .build();
    }

    @ApiOperation("Refresh tokens")
    @PostMapping("/token/refresh")
    public ResponseEntity<TokensDto> refreshTokens(@RequestHeader("refresh-token") String refreshToken) {
        val res = tokenService.refreshTokens(refreshToken);
        return ResponseEntity.ok(res);
    }
}
