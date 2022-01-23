package com.parcel.ms.courier.mapper;

import com.parcel.ms.courier.dao.CourierDao;
import com.parcel.ms.courier.model.CourierCreateDto;
import com.parcel.ms.courier.model.CourierDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel="spring")
public interface CourierMapper {
    CourierDto courierDaoToDto(CourierDao dto);
    @Mapping(source = "userId", target = "adminId")
    CourierDao courierCreateDtoToDao(Long userId, CourierCreateDto dto);

    List<CourierDto> courierDaoListToDto(List<CourierDao> daos);
}
