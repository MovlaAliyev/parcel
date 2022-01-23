package com.parcel.ms.auth.service;

import com.parcel.ms.auth.client.user.UserClient;
import com.parcel.ms.auth.client.user.UserDto;
import com.parcel.ms.auth.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService {

    private final UserClient userClient;
    private final AuthnService authnService;

    @Autowired
    public UserAuthService(
            UserClient userClient,
            AuthnService authnService) {
        this.userClient = userClient;
        this.authnService = authnService;
    }

    public SrpFirstStepRespDto srpAuthFirstStep(SrpStepFirstReqDto dto) {
        UserDto userDto = userClient.getUserByLogin(dto.getLogin());
        return authnService.srpAuthFirstStep(
                new User(userDto.getId(), userDto.getLogin(),
                        userDto.getEmail(), userDto.getSalt(), userDto.getVerifier()),
                dto
        );
    }

    public SrpSecondStepResDto srpAuthStep2(SrpSecondStepReqDto dto) {
        return authnService.srpAuthStep2(dto);
    }

}
