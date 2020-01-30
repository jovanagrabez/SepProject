package com.sep.nc.service;

import com.sep.nc.entity.Magazine;
import com.sep.nc.entity.dto.ShowMagazinesDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MagazineService {

    List<ShowMagazinesDto> getAll();
    Magazine getById(Long id);
}
