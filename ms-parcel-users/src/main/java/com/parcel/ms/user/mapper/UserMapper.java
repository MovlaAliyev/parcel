package com.parcel.ms.user.mapper;

import com.parcel.ms.user.dao.UserDao;
import com.parcel.ms.user.model.UserCreateDto;
import com.parcel.ms.user.model.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel="spring")
public interface UserMapper {
    UserDto userDaoToResponse(UserDao userDao);
    UserDao userDtoToDao(UserCreateDto userCreateRequest);

    void update(@MappingTarget UserDao entity, UserDto updateEntity);
}
