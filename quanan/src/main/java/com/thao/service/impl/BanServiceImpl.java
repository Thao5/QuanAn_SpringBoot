/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.service.impl;

import com.thao.pojo.Ban;
import com.thao.repository.BanRepository;
import com.thao.service.BanService;
import java.util.List;
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

    @Override
    public List<Ban> getBans() {
        return this.banRepo.findAll();
    }

    @Override
    public void save(Ban b) {
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
    
    
    
}
