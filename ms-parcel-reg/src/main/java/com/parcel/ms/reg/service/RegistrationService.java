package com.parcel.ms.reg.service;

import com.parcel.ms.reg.client.user.UserClient;
import com.parcel.ms.reg.client.user.enums.UserType;
import com.parcel.ms.reg.client.user.model.UserCreateDto;
import com.parcel.ms.reg.dao.RegistrationEntity;
import com.parcel.ms.reg.dao.RegistrationRepo;
import com.parcel.ms.reg.enums.RegistrationSteps;
import com.parcel.ms.reg.exceptions.RegistrationException;
import com.parcel.ms.reg.model.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Log4j2
@Service
public class RegistrationService {
    private final OtpService otpService;

    private final UserClient userClient;

    private final RegistrationRepo registrationRepo;

    private final RegistrationSessionService registrationSessionService;


    @Autowired
    public RegistrationService(
            UserClient userClient,
            OtpService otpService,
            RegistrationRepo registrationRepo,
            RegistrationSessionService registrationSessionService
    ) {
        this.userClient = userClient;
        this.otpService = otpService;
        this.registrationRepo = registrationRepo;
        this.registrationSessionService = registrationSessionService;
    }

    public OtpCreateResponse createOtp(OtpCreateRequest dto){
        OtpCreateResponse response = otpService.createOtp(dto);

        String login = UUID.randomUUID().toString();

        RegistrationEntity entity = registrationRepo.findByEmail(dto.getEmail())
                .orElseGet(()-> buildEntity(dto.getEmail(), login));

        if(entity.getStep() != RegistrationSteps.FINISH){
            entity.setStep(RegistrationSteps.OTP);
            registrationRepo.save(entity);
        }

        return response;
    }

    public void checkOtp(OtpCheckRequest dto){
        String email = otpService.checkOtp(dto);

        RegistrationEntity entity = registrationRepo.findByEmail(email)
                .orElseThrow(() -> new RegistrationException("error.RegistrationNotFound","Registration not found"));

        entity.setStep(RegistrationSteps.PERSONAL_INFO);

        registrationRepo.save(entity);
    }

    @Transactional
    public void saveUserInfo(UserInfoRequest dto){
        String email = registrationSessionService.getSessionData(dto.getSessionKey());

        RegistrationEntity entity = registrationRepo.findByEmail(email)
                .orElseThrow(() -> new RegistrationException("error.RegistrationNotFound","Registration not found"));

        if(entity.getStep() != RegistrationSteps.PERSONAL_INFO){
            throw new RegistrationException("error.InvalidRegistrationStep","Invalid registration step");
        }

        entity.setName(dto.getName());
        entity.setUserType(dto.getUserType());
        entity.setSurname(dto.getSurname());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setStep(RegistrationSteps.PASSWORD);

        registrationRepo.save(entity);
    }


    @Transactional
    public void savePassword(PasswordRequest dto){
        String email = registrationSessionService.getSessionData(dto.getSessionKey());

        RegistrationEntity entity = registrationRepo.findByEmail(email)
                .orElseThrow(() -> new RegistrationException("error.RegistrationNotFound","Registration not found"));

        if(entity.getStep() != RegistrationSteps.PASSWORD){
            throw new RegistrationException("error.InvalidRegistrationStep","Invalid registration step");
        }

        entity.setStep(RegistrationSteps.FINISH);

        registrationSessionService.deleteRegistrationSession(dto.getSessionKey());

        UserCreateDto userCreateDto = new UserCreateDto();

        userCreateDto.setEmail(email);
        userCreateDto.setSalt(dto.getSalt());
        userCreateDto.setName(entity.getName());
        userCreateDto.setLogin(entity.getLogin());
        userCreateDto.setVerifier(dto.getVerifier());
        userCreateDto.setSurname(entity.getSurname());
        userCreateDto.setUserType(entity.getUserType());
        userCreateDto.setPhoneNumber(entity.getPhoneNumber());

        userClient.createUser(userCreateDto);
    }

    private RegistrationEntity buildEntity(String email, String login){
        RegistrationEntity entity = new RegistrationEntity();
        entity.setEmail(email);
        entity.setLogin(login);
        entity.setStep(RegistrationSteps.OTP);
        return entity;
    }
}
