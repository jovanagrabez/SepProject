package com.sep.nc.service.impl;

import com.sep.nc.entity.Magazine;
import com.sep.nc.repository.MagazineRepository;
import com.sep.nc.service.MagazineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MagazineServiceImpl implements MagazineService {

    private final MagazineRepository magazineRepository;

    @Autowired
    public MagazineServiceImpl(MagazineRepository magazineRepository) {
        this.magazineRepository = magazineRepository;
    }

    @Override
    public List<Magazine> getAll() {
        return this.magazineRepository.findAll();
    }

    @Override
    public Magazine getById(Long id) {
        return this.magazineRepository.getOne(id);
    }
}
