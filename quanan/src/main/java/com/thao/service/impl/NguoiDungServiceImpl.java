/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.service.impl;

import com.thao.pojo.NguoiDung;
import com.thao.repository.NguoiDungRepository;
import com.thao.service.NguoiDungService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Chung Vu
 */
@Service
public class NguoiDungServiceImpl implements NguoiDungService{
    
    @Autowired
    private NguoiDungRepository ndRepo;

    @Override
    public List<NguoiDung> getNDs() {
        return this.ndRepo.findAll();
    }

    @Override
    public void save(NguoiDung nd) {
        this.ndRepo.save(nd);
    }

    @Override
    public void delete(Long id) {
        NguoiDung nd = this.ndRepo.getReferenceById(id);
        this.ndRepo.delete(nd);
    }

    @Override
    public NguoiDung getNguoiDungById(Long id) {
        return this.ndRepo.getReferenceById(id);
    }
    
    
}
