package com.sep.kp.service;

import com.sep.kp.model.Seller;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SellerService {

    List<Seller> getAllMethods(String client);

}
