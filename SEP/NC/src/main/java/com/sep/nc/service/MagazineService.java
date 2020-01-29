package com.sep.nc.service;

import com.sep.nc.entity.Magazine;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MagazineService {

    List<Magazine> getAll();
    Magazine getById(Long id);
}
