package com.parcel.ms.reg.dao;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RegistrationRepo extends CrudRepository<RegistrationEntity, Long> {
    Optional<RegistrationEntity> findByEmail(String email);
}
