/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.service.impl;

import com.thao.pojo.DanhGia;
import com.thao.repository.DanhGiaRepository;
import com.thao.service.DanhGiaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Chung Vu
 */
@Service
public class DanhGiaServiceImpl implements DanhGiaService{
    @Autowired
    private DanhGiaRepository dgRepo;

    @Override
    public List<DanhGia> getDanhGias() {
        return this.dgRepo.findAll();
    }

    @Override
    public void save(DanhGia dg) {
        this.dgRepo.save(dg);
    }

    @Override
    public void delete(Long id) {
        DanhGia dg = this.dgRepo.getReferenceById(id);
        this.dgRepo.delete(dg);
    }

    @Override
    public DanhGia getDanhGiaById(Long id) {
        return this.dgRepo.getReferenceById(id);
    }
    
    
}
