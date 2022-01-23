package com.parcel.ms.orders.mapper;

import com.parcel.ms.orders.dao.OrderDao;
import com.parcel.ms.orders.model.OrderDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface OrderMapper {
    OrderDao orderDtoToDao(OrderDto dto);
    OrderDto orderDaoToDto(OrderDao dao);

    List<OrderDto> orderDaoListToDtoList(List<OrderDao> daos);

}
