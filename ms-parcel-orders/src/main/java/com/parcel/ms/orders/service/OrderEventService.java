package com.parcel.ms.orders.service;

import com.parcel.ms.orders.dao.OrderDao;
import com.parcel.ms.orders.dao.OrderRepo;
import com.parcel.ms.orders.enums.OrderStatus;
import com.parcel.ms.orders.exceptions.OrderNotFoundException;
import com.parcel.ms.orders.mapper.OrderMapper;
import com.parcel.ms.orders.model.OrderCreateEvent;
import com.parcel.ms.orders.model.OrderDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Log4j2
@Service
public class OrderEventService {

    private final OrderRepo orderRepo;
    private final OrderMapper mapper;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public OrderEventService(
            OrderMapper mapper,
            OrderRepo orderRepo,
            ApplicationEventPublisher publisher) {
        this.mapper    = mapper;
        this.orderRepo = orderRepo;
        this.publisher = publisher;
    }

    @Transactional
    public void createOrder(long userId, OrderDto dto){
        OrderDao dao = mapper.orderDtoToDao(dto);

        dao.setUserId(userId);
        dao.setStatus(OrderStatus.CREATED);

        log.debug("Publishing  order {}", dao);

        log.debug("Saving an order {}", dao);
        orderRepo.save(dao);
    }

    @Transactional
    public void assignOrder(long orderId, long courierId){
        OrderDao dao = orderRepo.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException("error.orderNotFound", "Order not found"));

        dao.setStatus(OrderStatus.ASSIGNED);
        dao.setCourierId(courierId);

        log.debug("Assignee an order {}", dao);
        orderRepo.save(dao);
    }

    @Transactional
    public void cancelOrder(long orderId){
        OrderDao dao = orderRepo.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException("error.orderNotFound", "Order not found"));

        dao.setStatus(OrderStatus.CANCELED);

        log.debug("Canceling an order {}", dao);
        orderRepo.save(dao);
    }

    @Transactional
    public void onTheWayOrder(long orderId){
        OrderDao dao = orderRepo.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException("error.orderNotFound", "Order not found"));

        dao.setStatus(OrderStatus.ON_THE_WAY);

        log.debug("Canceling an order {}", dao);
        orderRepo.save(dao);
    }

    @Transactional
    public void doneOrder(long orderId){
        OrderDao dao = orderRepo.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException("error.orderNotFound", "Order not found"));

        dao.setStatus(OrderStatus.DELIVERED);

        log.debug("On the way an order {}", dao);
        orderRepo.save(dao);
    }
}
