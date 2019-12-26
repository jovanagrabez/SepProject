package com.sep.nc.service;

import com.sep.nc.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User getUserByEmail(String email);

}
