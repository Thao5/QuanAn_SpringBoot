/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.service.impl;

import com.thao.pojo.ChiNhanh;
import com.thao.repository.ChiNhanhRepository;
import com.thao.repository.CustomChiNhanhRepository;
import com.thao.service.ChiNhanhService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Thao
 */
@Service
public class ChiNhanhServiceImpl implements ChiNhanhService{
    @Autowired
    private ChiNhanhRepository storeRepo;
    @Autowired
    private CustomChiNhanhRepository cusStoreRepo;
    
    @Override
    public List<ChiNhanh> getChiNhanhs() {
        return storeRepo.findAll();
    }

//    @Override
//    public ChiNhanh getChiNhanhById(int id) {
//        return this.storeRepo.getChiNhanhById(id);
//    }
//
//    @Override
//    public boolean deleteStore(int id) {
//        return this.storeRepo.deleteStore(id);
//    }
//
//    @Override
//    public ChiNhanh getChiNhanhByUser(String username) {
//        return this.storeRepo.getChiNhanhByUser(username);
//    }

    @Override
    public void save(ChiNhanh cn) {
        this.storeRepo.save(cn);
    }

    @Override
    public void delete(Long id) {
        ChiNhanh cn = this.storeRepo.getReferenceById(id);
        this.storeRepo.delete(cn);
    }

    @Override
    public ChiNhanh getChiNhanhById(Long id) {
        return this.storeRepo.getReferenceById(id);
    }

    @Override
    public List<ChiNhanh> getChiNhanhTheoChuChiNhanh(int id) {
        return this.cusStoreRepo.getChiNhanhTheoChuChiNhanh(id);
    }
    
    
}
