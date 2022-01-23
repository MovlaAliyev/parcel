package com.parcel.ms.courier.service;

import com.parcel.ms.courier.client.user.UserClient;
import com.parcel.ms.courier.client.user.enums.UserType;
import com.parcel.ms.courier.client.user.model.UserDto;
import com.parcel.ms.courier.dao.CourierDao;
import com.parcel.ms.courier.dao.CourierRepo;
import com.parcel.ms.courier.enums.CourierStatus;
import com.parcel.ms.courier.exception.CourierCreateException;
import com.parcel.ms.courier.exception.CourierNotFoundException;
import com.parcel.ms.courier.exception.CourierPermissionException;
import com.parcel.ms.courier.exception.OrderStatusChangeException;
import com.parcel.ms.courier.mapper.CourierMapper;
import com.parcel.ms.courier.model.CourierCreateDto;
import com.parcel.ms.courier.model.CourierDto;
import com.parcel.ms.courier.model.OrderStatusDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class CourierService {

    private final UserClient userClient;

    private final CourierRepo courierRepo;
    private final CourierMapper courierMapper;

    private final CourierEventService courierEventService;

    @Autowired
    public CourierService(
            UserClient userClient,
            CourierRepo courierRepo,
            CourierMapper courierMapper,
            CourierEventService courierEventService
    ) {
        this.userClient = userClient;
        this.courierRepo = courierRepo;
        this.courierMapper = courierMapper;
        this.courierEventService = courierEventService;
    }

    public CourierDto createCourier(long userId, CourierCreateDto dto) {
        UserDto userDto = userClient.getUserById(userId);

        if(userDto.getUserType() != UserType.ADMIN) {
            throw new CourierPermissionException("error.InvalidPermission", "User doesnt have permission");
        }

        boolean result = courierRepo.existsByEmail(dto.getEmail());

        if(result) throw new CourierCreateException("error.userExists", "User already exists");

        CourierDao dao = courierMapper.courierCreateDtoToDao(userId, dto);
        dao.setCourierStatus(CourierStatus.IDLE);

        dao = courierRepo.save(dao);

        return courierMapper.courierDaoToDto(dao);
    }

    public List<CourierDto> getCourierList(long userId) {
        UserDto dto = userClient.getUserById(userId);

        if(dto.getUserType() != UserType.ADMIN) {
            throw new CourierPermissionException("error.InvalidPermission", "User doesnt have permission");
        }

        List<CourierDao> dao = courierRepo.findAll();

        return courierMapper.courierDaoListToDto(dao);
    }

    public List<CourierDto> getCourierListByStatus(long userId, CourierStatus courierStatus) {
        UserDto dto = userClient.getUserById(userId);

        if(dto.getUserType() != UserType.ADMIN) {
            throw new CourierPermissionException("error.InvalidPermission", "User doesn't have permission");
        }

        List<CourierDao> dao = courierRepo.findByCourierStatus(courierStatus);

        return courierMapper.courierDaoListToDto(dao);
    }

    public void assignee(long userId, OrderStatusDto dto) {
        CourierDao dao = courierRepo.findById(dto.getCourierId()).orElseThrow(
                () -> new CourierNotFoundException("error.CourierNotFound", "Courier not found"));

        UserDto userDto = userClient.getUserById(userId);

        if(userDto.getUserType() != UserType.ADMIN) {
            throw new CourierPermissionException("error.InvalidPermission", "User doesn't have permission");
        }

        if(dao.getCourierStatus() != CourierStatus.IDLE) {
            throw new OrderStatusChangeException("error.InvalidOrderStatus","Cannot assign order to courier");
        }

        courierEventService.publishOrderAssigned(dto.getCourierId(), dto.getOrderId());

        dao.setCourierStatus(CourierStatus.ASSIGNED);

        courierRepo.save(dao);
    }

    public void onTheWay(long userId, long orderId) {
        CourierDao dao = courierRepo.findById(userId).orElseThrow(
                () -> new CourierNotFoundException("error.CourierNotFound", "Courier not found"));

        courierEventService.publishOrderOnTheWay(userId, orderId);

        /*if(dao.getCourierStatus() != CourierStatus.ASSIGNED) {
            throw new OrderStatusChangeException("error.InvalidOrderStatus","Cannot change status");
        }*/

        dao.setCourierStatus(CourierStatus.ON_THE_WAY);

        courierRepo.save(dao);
    }

    public void cancel(long userId, long orderId) {
        CourierDao dao = courierRepo.findById(userId).orElseThrow(
                () -> new CourierNotFoundException("error.CourierNotFound", "Courier not found"));

        /*if(dao.getCourierStatus() != CourierStatus.ASSIGNED) {
            throw new OrderStatusChangeException("error.InvalidOrderStatus","Cannot cancel order status");
        }*/

        courierEventService.publishOrderCanceled(userId, orderId);

        dao.setCourierStatus(CourierStatus.IDLE);

        courierRepo.save(dao);
    }

    public void cancelOrderByUser(long courierId) {
        CourierDao dao = courierRepo.findById(courierId).orElseThrow(
                () -> new CourierNotFoundException("error.CourierNotFound", "Courier not found"));

        dao.setCourierStatus(CourierStatus.IDLE);

        courierRepo.save(dao);
    }

    public void delivered(long userId, long orderId) {
        CourierDao dao = courierRepo.findById(userId).orElseThrow(
                () -> new CourierNotFoundException("error.CourierNotFound", "Courier not found"));

        /*if(dao.getCourierStatus() != CourierStatus.ON_THE_WAY) {
            throw new OrderStatusChangeException("error.InvalidOrderStatus", "Cannot deliver order");
        }*/

        courierEventService.publishOrderDelivered(userId, orderId);

        dao.setCourierStatus(CourierStatus.IDLE);

        courierRepo.save(dao);
    }

    public CourierDto getCourierByLogin(String login){
        CourierDao dao = courierRepo.findByLogin(login).orElseThrow(
                () -> new CourierNotFoundException("error.CourierNotFound", "Courier not found"));

        return courierMapper.courierDaoToDto(dao);
    }

}
