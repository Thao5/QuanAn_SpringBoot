/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.service.impl;

import com.thao.pojo.QuyDinhVanChuyen;
import com.thao.repository.QuyDinhVanChuyenRepository;
import com.thao.service.QuyDinhVanChuyenService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Chung Vu
 */
@Service
public class QuyDinhVanChuyenServiceImpl implements QuyDinhVanChuyenService{
    @Autowired
    private QuyDinhVanChuyenRepository qdRepo;

    @Override
    public List<QuyDinhVanChuyen> getQuyDinhs() {
        return this.qdRepo.findAll();
    }

    @Override
    public void save(QuyDinhVanChuyen qd) {
        this.qdRepo.save(qd);
    }

    @Override
    public void delete(Long id) {
        QuyDinhVanChuyen qd = this.qdRepo.getReferenceById(id);
        this.qdRepo.delete(qd);
    }

    @Override
    public QuyDinhVanChuyen getDinhVanChuyenById(Long id) {
        return this.qdRepo.getReferenceById(id);
    }
    
    
}
