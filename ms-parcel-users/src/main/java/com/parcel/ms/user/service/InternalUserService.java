package com.parcel.ms.user.service;

import com.parcel.ms.user.dao.UserDao;
import com.parcel.ms.user.dao.UserRepo;
import com.parcel.ms.user.enums.UserStatus;
import com.parcel.ms.user.exceptions.UserCreateException;
import com.parcel.ms.user.exceptions.UserException;
import com.parcel.ms.user.mapper.UserMapper;
import com.parcel.ms.user.model.UserCreateDto;
import com.parcel.ms.user.model.UserDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class InternalUserService {

    private final UserRepo userRepo;
    private final UserMapper mapper;

    @Autowired
    public InternalUserService(
            UserRepo userRepo,
            UserMapper mapper) {
        this.mapper   = mapper;
        this.userRepo = userRepo;
    }

    public void createUser(UserCreateDto req) {
        log.debug("createUser -- surname: {} name: {}", req.getSurname(), req.getName());
        boolean exists = userRepo.existsByLogin(req.getLogin());

        if(exists) throw new UserCreateException("error.userExists", "User already exists");

        log.debug("createUser save user to db");
        UserDao userDao = mapper.userDtoToDao(req);
        userDao.setUserStatus(UserStatus.ACTIVE);

        userRepo.save(userDao);
    }

    public UserDto getUserById(long userId) {
        log.debug("Get user info by userid {}", userId);
        UserDao dao = userRepo.findById(userId).orElseThrow(
                () -> new UserException("error.userNotFound", "User not found"));

        return mapper.userDaoToResponse(dao);
    }

    public UserDto getUserByLogin(String login) {
        log.debug("Get user info by login {}", login);
        UserDao dao = userRepo.findUserByLogin(login).orElseThrow(
                () -> new UserException("error.userNotFound", "User not found"));

        return mapper.userDaoToResponse(dao);
    }
}
