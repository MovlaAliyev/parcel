package com.parcel.ms.reg.client.otp;

import com.parcel.ms.reg.model.OtpCheckClientRequest;
import com.parcel.ms.reg.client.otp.model.OtpCreateClientRequest;
import com.parcel.ms.reg.client.otp.model.OtpCreateClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-parcel-otp", url = "${feign.client.config.ms-parcel-otp.url}", decode404 = true)
public interface OtpClient {
    @PostMapping(value = "/v1/internal/otp/check")
    void checkOtp(@RequestBody OtpCheckClientRequest dto);

    @PostMapping(value = "/v1/internal/otp/create")
    OtpCreateClientResponse createOtp(@RequestBody OtpCreateClientRequest dto);
}
