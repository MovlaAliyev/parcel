package com.parcel.ms.user.controller;

import com.parcel.ms.user.model.UserDto;
import com.parcel.ms.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/user")
public class PublicController {

    private final UserService userService;

    @Autowired
    public PublicController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/id")
    public UserDto getUserById (@RequestHeader("parcel-user-id") long userId){
        return userService.getUserById(userId);
    }

    @GetMapping("/login/{login}")
    public UserDto getUserByLogin (
            @RequestHeader("parcel-user-id") long userId,
            @PathVariable("login") String login){
        return userService.getUserByLogin(login);
    }

    @PostMapping("/patch")
    public UserDto patchUser(
            @RequestHeader("parcel-user-id") long userId,
            @RequestBody UserDto userDto){
        return userService.patchUser(userId, userDto);
    }

}
