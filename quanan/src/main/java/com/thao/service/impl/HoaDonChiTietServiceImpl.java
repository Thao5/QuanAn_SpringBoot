/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.service.impl;

import com.thao.pojo.HoaDonChiTiet;
import com.thao.repository.HoaDonChiTietRepository;
import com.thao.service.HoaDonChiTietService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Chung Vu
 */
@Service
public class HoaDonChiTietServiceImpl implements HoaDonChiTietService{
    @Autowired
    private HoaDonChiTietRepository hdRepo;

    @Override
    public List<HoaDonChiTiet> getHDs() {
        return this.hdRepo.findAll();
    }

    @Override
    public void save(HoaDonChiTiet hd) {
        if(hd.getId() == null){
            hd.setCreatedDate(new Date());
        }
        this.hdRepo.save(hd);
    }

    @Override
    public void delete(Long id) {
        HoaDonChiTiet hd = this.hdRepo.getReferenceById(id);
        this.hdRepo.delete(hd);
    }

    @Override
    public HoaDonChiTiet getHoaDonChiTietById(Long id) {
        return this.hdRepo.getReferenceById(id);
    }
    
    
    
}
