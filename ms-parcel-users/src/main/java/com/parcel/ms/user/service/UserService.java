package com.parcel.ms.user.service;

import com.parcel.ms.user.dao.UserDao;
import com.parcel.ms.user.dao.UserRepo;
import com.parcel.ms.user.exceptions.UserException;
import com.parcel.ms.user.mapper.UserMapper;
import com.parcel.ms.user.model.UserDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserService {

    private final UserRepo userRepo;
    private final UserMapper mapper;

    @Autowired
    public UserService(UserRepo userRepo, UserMapper mapper) {
        this.mapper = mapper;
        this.userRepo = userRepo;
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

    public UserDto patchUser(long userId, UserDto userDto) {
        log.debug("patchUser -- userId: {} name: {}", userId, userDto.getName());
        UserDao userDao = userRepo.findById(userId).orElseThrow(()
                -> new UserException("error.userNotFound", "User not found"));

        mapper.update(userDao, userDto);

        userRepo.save(userDao);

        return mapper.userDaoToResponse(userDao);
    }
}
