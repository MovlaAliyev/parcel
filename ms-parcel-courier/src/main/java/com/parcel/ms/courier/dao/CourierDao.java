package com.parcel.ms.courier.dao;

import com.parcel.ms.courier.enums.CourierStatus;
import com.parcel.ms.courier.enums.CourierTypes;
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
@Table(name = "couriers")
public class CourierDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String salt;
    private String login;
    private String verifier;

    private String email;
    private String name;
    private String surname;
    private String phoneNumber;

    private long adminId;

    @Enumerated(EnumType.STRING)
    private CourierTypes courierType;
    @Enumerated(EnumType.STRING)
    private CourierStatus courierStatus;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
