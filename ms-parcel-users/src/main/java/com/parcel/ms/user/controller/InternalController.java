package com.parcel.ms.user.controller;

import com.parcel.ms.user.model.UserCreateDto;
import com.parcel.ms.user.model.UserDto;
import com.parcel.ms.user.service.InternalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/internal/users")
public class InternalController {

    private final InternalUserService userService;

    @Autowired
    public InternalController(InternalUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public void createUser (@RequestBody UserCreateDto userCreateRequest){
        userService.createUser(userCreateRequest);
    }

    @GetMapping("/id/{userId}")
    public UserDto getUserById (@PathVariable("userId") long userId){
        return userService.getUserById(userId);
    }

    @GetMapping("/login/{login}")
    public UserDto getUserByLogin (@PathVariable("login") String login){
        return userService.getUserByLogin(login);
    }

}
