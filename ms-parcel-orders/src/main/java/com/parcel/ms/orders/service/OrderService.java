package com.parcel.ms.orders.service;

import com.parcel.ms.orders.client.UserClient;
import com.parcel.ms.orders.dao.OrderDao;
import com.parcel.ms.orders.dao.OrderRepo;
import com.parcel.ms.orders.enums.OrderStatus;
import com.parcel.ms.orders.client.enums.UserType;
import com.parcel.ms.orders.exceptions.OrderChangeException;
import com.parcel.ms.orders.exceptions.OrderNotFoundException;
import com.parcel.ms.orders.exceptions.OrderPermissionException;
import com.parcel.ms.orders.mapper.OrderMapper;
import com.parcel.ms.orders.model.OrderDestinationUpdateDto;
import com.parcel.ms.orders.model.OrderDto;
import com.parcel.ms.orders.client.model.UserDto;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class OrderService {

    private final OrderMapper mapper;
    private final OrderRepo orderRepo;

    private final UserClient userClient;

    private final Logger log = LoggerFactory.getLogger(Order.class);

    @Autowired
    public OrderService(
            OrderMapper mapper,
            OrderRepo orderRepo,
            UserClient userClient) {
        this.mapper     = mapper;
        this.orderRepo  = orderRepo;
        this.userClient = userClient;
    }

    public OrderDto getDetail(long userId, long orderId) {
        log.debug("Getting user id {} order details", userId);
        OrderDao dao = orderRepo.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException("error.orderNotFound", "Order not found"));

        return mapper.orderDaoToDto(dao);
    }

    public void updateDestination(long userId, OrderDestinationUpdateDto dto) {
        log.debug("Getting user id {} order details", userId);
        OrderDao dao = orderRepo.findById(userId).orElseThrow(
                () -> new OrderNotFoundException("error.orderNotFound", "Order not found"));

        if(dao.getStatus() != OrderStatus.CREATED || dao.getStatus() == OrderStatus.ASSIGNED)
            throw new OrderChangeException("error.InvalidStatus", "Order destination cannot be changed");

        dao.setDestinationLat(dto.getLat());
        dao.setDestinationLon(dto.getLon());

        orderRepo.save(dao);
    }

    public List<OrderDto> getOrderHistory(long userId) {
        List<OrderDao> orders = orderRepo.findAllByUserId(userId);

        return mapper.orderDaoListToDtoList(orders);
    }

    public List<OrderDto> getOrders(long userId) {
        UserDto userDto = userClient.getUserById(userId);

        if(userDto.getUserType() != UserType.ADMIN)
            throw new OrderPermissionException("error.InvalidPermission", "User doesnt have permission");

        List<OrderDao> orders = orderRepo.findAllByStatusNotIn(
                Arrays.asList(OrderStatus.CANCELED, OrderStatus.DELIVERED));

        return mapper.orderDaoListToDtoList(orders);
    }

}