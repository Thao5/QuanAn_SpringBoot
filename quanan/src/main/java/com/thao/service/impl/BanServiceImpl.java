/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.service.impl;

import com.thao.pojo.Ban;
import com.thao.repository.BanRepository;
import com.thao.repository.CustomBanRepository;
import com.thao.service.BanService;
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
public class BanServiceImpl implements BanService{
    @Autowired
    private BanRepository banRepo;
    @Autowired
    private CustomBanRepository cBanRepo;

    @Override
    public List<Ban> getBans() {
        return this.banRepo.findAll();
    }

    @Override
    public void save(Ban b) {
        if(b.getId() == null) b.setCreatedDate(new Date());
        this.banRepo.save(b);
    }

    @Override
    public void delete(Long id) {
        Ban b = this.banRepo.getReferenceById(id);
        this.banRepo.delete(b);
    }

    @Override
    public Ban getBanById(Long id) {
        return this.banRepo.getReferenceById(id);
    }

    @Override
    public List<Ban> getBanTheoChiNhanh(int cnId) {
        return this.cBanRepo.getBanTheoChiNhanh(cnId);
    }

    @Override
    public List<Ban> getBanCus(Map<String, String> params) {
        return this.cBanRepo.getBanCus(params);
    }
    
    
    
}
