package com.parcel.ms.reg.dao;

import com.parcel.ms.reg.client.user.enums.UserType;
import com.parcel.ms.reg.enums.RegistrationSteps;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "registration")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RegistrationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;
    private String phoneNumber;

    private String name;
    private String surname;

    private String login;

    @Enumerated(EnumType.STRING)
    private RegistrationSteps step;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
