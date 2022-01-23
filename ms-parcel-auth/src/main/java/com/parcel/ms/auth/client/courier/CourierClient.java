package com.parcel.ms.auth.client.courier;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-parcel-courier", url = "${feign.client.config.ms-parcel-courier.url}", decode404 = true)
public interface CourierClient {
    @GetMapping("/v1/internal/courier/login/{login}")
    CourierDto getCourierByLogin(@PathVariable("login") String login);
}
