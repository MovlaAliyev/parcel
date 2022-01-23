package com.parcel.ms.orders.controller;

import com.parcel.ms.orders.model.OrderDestinationUpdateDto;
import com.parcel.ms.orders.model.OrderDto;
import com.parcel.ms.orders.service.OrderEventService;
import com.parcel.ms.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderEventService orderEventService;

    @Autowired
    public OrderController(
            OrderService orderService,
            OrderEventService orderEventService) {
        this.orderService = orderService;
        this.orderEventService = orderEventService;
    }

    @PostMapping
    public void createOrder(
            @RequestHeader("parcel-user-id") long userId,
            @RequestBody OrderDto dto
    ) {
        orderEventService.createOrder(userId, dto);
    }

    @GetMapping("/order/{orderId}")
    public OrderDto getDetail(
            @RequestHeader("parcel-user-id") long userId,
            @PathVariable("orderId") long orderId
    ) {
        return orderService.getDetail(userId, orderId);
    }

    @PutMapping("/change/destination")
    public void updateDestination(
            @RequestHeader("parcel-user-id") long userId,
            @RequestBody OrderDestinationUpdateDto dto) {
        orderService.updateDestination(userId, dto);
    }

    @GetMapping("/history/order")
    public List<OrderDto> orderHistory(
            @RequestHeader("parcel-user-id") long userId
    ) {
        return orderService.getOrderHistory(userId);
    }

    @GetMapping("/orders")
    public List<OrderDto> orders(
            @RequestHeader("parcel-user-id") long userId
    ) {
        return orderService.getOrders(userId);
    }

}
