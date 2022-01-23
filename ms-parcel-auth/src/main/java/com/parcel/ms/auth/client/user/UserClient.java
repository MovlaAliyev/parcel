package com.parcel.ms.auth.client.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "ms-parcel-users", url = "${feign.client.config.ms-parcel-users.url}", decode404 = true)
public interface UserClient {
    @GetMapping("v1/internal/users/login/{login}")
    UserDto getUserByLogin(@PathVariable("login") String login);
}
