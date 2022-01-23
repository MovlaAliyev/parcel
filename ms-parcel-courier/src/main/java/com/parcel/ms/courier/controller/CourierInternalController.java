package com.parcel.ms.courier.controller;

import com.parcel.ms.courier.model.CourierDto;
import com.parcel.ms.courier.service.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/internal/courier")
public class CourierInternalController {

    private final CourierService courierService;

    @Autowired
    public CourierInternalController(CourierService courierService) {
        this.courierService = courierService;
    }

    @GetMapping("/login/{login}")
    public CourierDto getCourierByLogin(@PathVariable("login") String login){
        return courierService.getCourierByLogin(login);
    }
}
