package com.parcel.ms.orders.dao;

import com.parcel.ms.orders.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "orders")
public class OrderDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long userId;

    private long courierId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private double pickupLat;
    private double pickupLon;

    private double destinationLat;
    private double destinationLon;

    private String instruction;

    private double packageWeight;

    private String pickupPhoneNumber;
    private String deliveryPhoneNumber;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
