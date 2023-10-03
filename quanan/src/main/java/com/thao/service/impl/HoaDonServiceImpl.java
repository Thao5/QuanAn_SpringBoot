/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.service.impl;

import com.thao.pojo.HoaDon;
import com.thao.repository.HoaDonRepository;
import com.thao.service.HoaDonService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Chung Vu
 */
@Service
public class HoaDonServiceImpl implements HoaDonService{
    @Autowired
    private HoaDonRepository hdRepo;

    @Override
    public List<HoaDon> getHoaDons() {
        return this.hdRepo.findAll();
    }

    @Override
    public void save(HoaDon hd) {
        if(hd.getId() == null){
            hd.setCreatedDate(new Date());
        }
        this.hdRepo.save(hd);
    }

    @Override
    public void delete(Long id) {
        HoaDon hd = this.hdRepo.getReferenceById(id);
        this.hdRepo.delete(hd);;
    }

    @Override
    public HoaDon getHoaDonById(Long id) {
        return this.hdRepo.getReferenceById(id);
    }
    
    
    
}
