package com.parcel.ms.reg.client.user;

import com.parcel.ms.reg.client.user.model.UserCreateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-parcel-users", url = "${feign.client.config.ms-parcel-users.url}", decode404 = true)
public interface UserClient {
    @PostMapping("/v1/internal/users/")
    void createUser(@RequestBody UserCreateDto dto);
}
