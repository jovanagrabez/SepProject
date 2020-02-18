package com.sep.nc.service;

import com.sep.nc.entity.Magazine;
import com.sep.nc.entity.User;
import com.sep.nc.entity.dto.UserDto;
import com.sep.nc.entity.enumeration.PurchaseStatus;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserDto getUserByEmail(String email);
    User saveUser(User user);

    // TODO remove from user service
    void setMagazinePurchaseStatus(String email, Long purchaseId, PurchaseStatus purchaseStatus);

}
