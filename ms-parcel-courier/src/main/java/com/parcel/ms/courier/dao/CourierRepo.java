package com.parcel.ms.courier.dao;

import com.parcel.ms.courier.enums.CourierStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourierRepo extends JpaRepository<CourierDao, Long> {
    Optional<CourierDao> findByLogin(String login);
    boolean existsByEmail(String login);
    List<CourierDao> findByCourierStatus(CourierStatus courierStatus);
}
