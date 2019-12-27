package com.sep.nc.service.impl;

import com.sep.nc.entity.Magazine;
import com.sep.nc.entity.User;
import com.sep.nc.repository.MagazineRepository;
import com.sep.nc.repository.UserRepository;
import com.sep.nc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MagazineRepository magazineRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, MagazineRepository magazineRepository) {
        this.userRepository = userRepository;
        this.magazineRepository = magazineRepository;
    }

    @Override
    public User getUserByEmail(String email) {
        return this.userRepository.findUserByEmail(email);
    }

    @Override
    public User addBoughtMagazine(String email, Long magazineId) {

        Magazine magazine = this.magazineRepository.getOne(magazineId);
        User user = this.userRepository.findUserByEmail(email);
        if (user.getPayedMagazines().isEmpty()) {
            user.setPayedMagazines(new ArrayList<Magazine>());
        }
        user.getPayedMagazines().add(magazine);

        return this.userRepository.save(user);
    }
}
