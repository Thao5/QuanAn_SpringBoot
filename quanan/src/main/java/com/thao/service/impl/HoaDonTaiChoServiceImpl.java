/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.service.impl;

import com.thao.pojo.HoaDonTaiCho;
import com.thao.repository.CustomHoaDonTaiChoRepository;
import com.thao.repository.HoaDonTaiChoRepository;
import com.thao.service.HoaDonTaiChoService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Chung Vu
 */
@Service
public class HoaDonTaiChoServiceImpl implements HoaDonTaiChoService{
    @Autowired
    private HoaDonTaiChoRepository hdRepo;
    @Autowired
    private CustomHoaDonTaiChoRepository cusHdRepo;

    @Override
    public List<HoaDonTaiCho> getHoaDons() {
        return this.hdRepo.findAll();
    }

    @Override
    public void save(HoaDonTaiCho hd) {
        if(hd.getId() == null){
            hd.setCreatedDate(new Date());
        }
        this.hdRepo.save(hd);
    }

    @Override
    public void delete(Long id) {
        HoaDonTaiCho hd = this.hdRepo.getReferenceById(id);
        this.hdRepo.delete(hd);
    }

    @Override
    public HoaDonTaiCho getHoaDonById(Long id) {
        return this.hdRepo.getReferenceById(id);
    }

    @Override
    public List<HoaDonTaiCho> getHDs(Map<String, String> params) {
        return this.cusHdRepo.getHDs(params);
    }
    
}
