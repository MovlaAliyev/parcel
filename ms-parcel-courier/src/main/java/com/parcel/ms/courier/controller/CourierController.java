package com.parcel.ms.courier.controller;

import com.parcel.ms.courier.enums.CourierStatus;
import com.parcel.ms.courier.model.CourierCreateDto;
import com.parcel.ms.courier.model.CourierDto;
import com.parcel.ms.courier.model.OrderStatusDto;
import com.parcel.ms.courier.service.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/courier")
public class CourierController {

    private final CourierService service;

    @Autowired
    public CourierController(CourierService service) {
        this.service = service;
    }

    @PostMapping
    public CourierDto createCourier(
            @RequestHeader("parcel-user-id") long userId,
            @RequestBody CourierCreateDto dto) {
        return service.createCourier(userId, dto);
    }

    @GetMapping("/list")
    public List<CourierDto> courierList(
            @RequestHeader("parcel-user-id") long userId) {
        return service.getCourierList(userId);
    }

    @GetMapping("/list/{status}/status")
    public List<CourierDto> courierListByStatus(
            @RequestHeader("parcel-user-id") long userId,
            @PathVariable("status") CourierStatus status) {
        return service.getCourierListByStatus(userId, status);
    }

    @PostMapping("/assignee")
    public void assignee(
            @RequestHeader("parcel-user-id") long userId,
            @RequestBody OrderStatusDto dto){
        service.assignee(userId, dto);
    }

    @PostMapping("/status/order/{orderId}/cancel/")
    public void cancel(
            @RequestHeader("parcel-user-id") long userId,
            @PathVariable("orderId") long orderId){
        service.cancel(userId, orderId);
    }

    @PostMapping("/status/order/{orderId}/onway/")
    public void onway(
            @RequestHeader("parcel-user-id") long userId,
            @PathVariable("orderId") long orderId){
        service.onTheWay(userId, orderId);
    }

    @PostMapping("/status/order/{orderId}/deliver/")
    public void deliver(
            @RequestHeader("parcel-user-id") long userId,
            @PathVariable("orderId") long orderId){
        service.delivered(userId, orderId);
    }

}
