package com.parcel.ms.orders.dao;

import com.parcel.ms.orders.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<OrderDao, Long> {
    Optional<OrderDao> findByUserId(long userId);
    List<OrderDao> findAllByUserId(long userId);
    List<OrderDao> findAllByStatusNotIn(List<OrderStatus> statuses);
}
