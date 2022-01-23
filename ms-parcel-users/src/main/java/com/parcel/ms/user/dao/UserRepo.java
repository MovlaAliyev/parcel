package com.parcel.ms.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserDao, Long> {
    boolean existsByLogin(String login);
    Optional<UserDao> findUserByLogin(String login);

}
