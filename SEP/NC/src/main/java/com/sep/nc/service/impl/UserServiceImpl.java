package com.sep.nc.service.impl;

import com.sep.nc.entity.MagazinePurchase;
import com.sep.nc.entity.User;
import com.sep.nc.entity.enumeration.PurchaseStatus;
import com.sep.nc.repository.MagazinePurchasesRepository;
import com.sep.nc.repository.MagazineRepository;
import com.sep.nc.repository.UserRepository;
import com.sep.nc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
//    private final MagazineRepository magazineRepository;
    private final MagazinePurchasesRepository magazinePurchasesRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, MagazinePurchasesRepository magazinePurchasesRepository) {
        this.userRepository = userRepository;
        this.magazinePurchasesRepository = magazinePurchasesRepository;
    }

    @Override
    public User getUserByEmail(String email) {
        return this.userRepository.findUserByEmail(email);
    }

    @Override
    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public void setMagazinePurchaseStatus(String email, Long purchaseId, PurchaseStatus purchaseStatus) {

        MagazinePurchase magazinePurchase = this.magazinePurchasesRepository.getOne(purchaseId);
        magazinePurchase.setStatus(purchaseStatus);

        magazinePurchasesRepository.save(magazinePurchase);
//        User user = this.userRepository.findUserByEmail(email);
//        if (user.getMagazinePurchases().isEmpty()) {
//            // TODO proveriti da li se dodaje status payed i kada se ova metoda poziva
//            user.setMagazinePurchases(new ArrayList<>());
//        }
//        user.getMagazinePurchases().add(magazine);
//
//        return this.userRepository.save(user);
    }
}
