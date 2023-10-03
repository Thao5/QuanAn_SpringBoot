/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.service.impl;

import com.thao.pojo.HoaDonChiTietTaiCho;
import com.thao.repository.HoaDonChiTietTaiChoRepository;
import com.thao.service.HoaDonChiTietTaiChoService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Chung Vu
 */
@Service
public class HoaDonChiTietTaiChoServiceImpl implements HoaDonChiTietTaiChoService{
    @Autowired
    private HoaDonChiTietTaiChoRepository hdRepo;
    
    @Override
    public List<HoaDonChiTietTaiCho> getHDs() {
        return this.hdRepo.findAll();
    }

    @Override
    public void save(HoaDonChiTietTaiCho hd) {
        if(hd.getId() == null){
            hd.setCreatedDate(new Date());
        }
        this.hdRepo.save(hd);
    }

    @Override
    public void delete(Long id) {
        HoaDonChiTietTaiCho hd = this.hdRepo.getReferenceById(id);
        this.hdRepo.delete(hd);
    }

    @Override
    public HoaDonChiTietTaiCho getHoaDonChiTietById(Long id) {
        return this.hdRepo.getReferenceById(id);
    }
}
