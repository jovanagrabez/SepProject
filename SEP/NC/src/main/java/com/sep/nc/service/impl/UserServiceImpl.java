package com.sep.nc.service.impl;

import com.sep.nc.common.PasswordChecker;
import com.sep.nc.entity.MagazinePurchase;
import com.sep.nc.entity.User;
import com.sep.nc.entity.dto.UserDto;
import com.sep.nc.entity.enumeration.PurchaseStatus;
import com.sep.nc.repository.MagazinePurchasesRepository;
import com.sep.nc.repository.MagazineRepository;
import com.sep.nc.repository.UserRepository;
import com.sep.nc.service.UserService;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    private static final Argon2 ARGON2 = Argon2Factory.create();

    private static final int ITERATIONS = 2;
    private static final int MEMORY= 65536;
    private static final int PARALLELISM = 1;
    private final UserRepository userRepository;
//    private final MagazineRepository magazineRepository;
    private final MagazinePurchasesRepository magazinePurchasesRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, MagazinePurchasesRepository magazinePurchasesRepository) {
        this.userRepository = userRepository;
        this.magazinePurchasesRepository = magazinePurchasesRepository;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return new UserDto(this.userRepository.findUserByEmail(email));
    }

    @Override
    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public User registerUser(UserDto userDto) {
        if (PasswordChecker.check10000FrequentlyUsed(userDto.getPassword())) {
            User user = new User();
            user.setName(userDto.getName());
            user.setLastName(userDto.getLastName());
            user.setEmail(userDto.getEmail());
            user.setPassword(ARGON2.hash(ITERATIONS, MEMORY, PARALLELISM, userDto.getPassword()));
            user.setCity(userDto.getCity());
            user.setCountry(userDto.getCountry());
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public void setMagazinePurchaseStatus(String email, Long purchaseId, PurchaseStatus purchaseStatus) {

        MagazinePurchase magazinePurchase = this.magazinePurchasesRepository.getOne(purchaseId);
        magazinePurchase.setStatus(purchaseStatus);

        magazinePurchasesRepository.save(magazinePurchase);

    }
}
