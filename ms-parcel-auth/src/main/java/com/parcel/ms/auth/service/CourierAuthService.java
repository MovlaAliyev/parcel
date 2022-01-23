package com.parcel.ms.auth.service;

import com.parcel.ms.auth.client.courier.CourierClient;
import com.parcel.ms.auth.client.courier.CourierDto;
import com.parcel.ms.auth.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourierAuthService {

    private final CourierClient courierClient;
    private final AuthnService authnService;

    @Autowired
    public CourierAuthService(
            CourierClient courierClient,
            AuthnService authnService) {
        this.courierClient = courierClient;
        this.authnService = authnService;
    }

    public SrpFirstStepRespDto srpAuthFirstStep(SrpStepFirstReqDto dto) {
        CourierDto courierDto = courierClient.getCourierByLogin(dto.getLogin());
        return authnService.srpAuthFirstStep(
                new User(courierDto.getId(), courierDto.getLogin(),
                        courierDto.getEmail(), courierDto.getSalt(), courierDto.getVerifier()),
                dto
        );
    }

    public SrpSecondStepResDto srpAuthStep2(SrpSecondStepReqDto dto) {
        return authnService.srpAuthStep2(dto);
    }
}
