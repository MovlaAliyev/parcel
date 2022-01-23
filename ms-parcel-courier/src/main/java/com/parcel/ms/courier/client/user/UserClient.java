package com.parcel.ms.courier.client.user;

import com.parcel.ms.courier.client.user.model.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-parcel-users", url = "${feign.client.config.ms-parcel-users.url}", decode404 = true)
public interface UserClient {
    @GetMapping("v1/internal/users/id/{userId}")
    UserDto getUserById(@PathVariable("userId") long userId);
}
