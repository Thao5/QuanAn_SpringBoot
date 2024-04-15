/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.thao.pojo.ChiNhanh;
import com.thao.pojo.NguoiDung;
import com.thao.repository.ChiNhanhRepository;
import com.thao.repository.CustomChiNhanhRepository;
import com.thao.service.ChiNhanhService;
import com.thao.service.EmailService;
import com.thao.service.NguoiDungService;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Thao
 */
@Service
public class ChiNhanhServiceImpl implements ChiNhanhService {

    @Autowired
    private ChiNhanhRepository storeRepo;
    @Autowired
    private CustomChiNhanhRepository cusStoreRepo;
    @Autowired
    private Cloudinary cloudinary;
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
        if (!cn.getFile().isEmpty()) {
            Map res;
            try {
                res = this.cloudinary.uploader().upload(cn.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                cn.setImage(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(ChiNhanhServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.storeRepo.save(cn);
    }

    @Override
    public void delete(ChiNhanh cn) {
        this.storeRepo.delete(cn);
    }

    @Override
    public ChiNhanh getChiNhanhById(Long id) {
        return this.storeRepo.getReferenceById(id);
    }
    
    @Override
    public ChiNhanh getChiNhanhById2(Long id) {
        return this.storeRepo.findById(id).get();
    }

    @Override
    public List<ChiNhanh> getChiNhanhTheoChuChiNhanh(int id) {
        return this.cusStoreRepo.getChiNhanhTheoChuChiNhanh(id);
    }

    @Override
    public List<ChiNhanh> getChiNhanhs(Map<String, String> params) {
        return this.cusStoreRepo.getChiNhanhs(params);
    }

}
