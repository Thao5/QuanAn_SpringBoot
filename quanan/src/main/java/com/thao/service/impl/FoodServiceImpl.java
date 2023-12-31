/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.thao.pojo.ThucAn;
import com.thao.repository.CustomThucAnRepository;
import com.thao.repository.FoodRepository;
import com.thao.service.ChiNhanhService;
import com.thao.service.FoodService;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Thao
 */
@Service
public class FoodServiceImpl implements FoodService{

    @Autowired
    private FoodRepository foodRepo;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private CustomThucAnRepository cfoodRepo;
    
    @Override
    public List<ThucAn> getThucAns() {
        return foodRepo.findAll();
    }
    
    

////    @Override
////    public ThucAn getThucAnByChiNhanh(int id) {
////        return this.foodRepo.getThucAnByChiNhanh(id);
////    }
//
//    @Override
//    public List<ThucAn> getThucAnByChiNhanh(int id) {
//        return this.foodRepo.getThucAnByChiNhanh(id);
//    }
//
//    @Override
//    public ThucAn getThucAnById(int id) {
//        return this.foodRepo.getThucAnById(id);
//    }
//
////    @Override
////    public boolean addOrUpdateFood(ThucAn f) {
////        if (!f.getFile().isEmpty()) {
////            try {
////                Map res = this.cloudinary.uploader().upload(f.getFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
////                f.setImage(res.get("secure_url").toString());
////            } catch (IOException ex) {
////                Logger.getLogger(FoodServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
////            }
////        } 
////        return this.foodRepo.addOrUpdateFood(f);
////    }
//
//    @Override
//    public boolean deleteFood(int id) {
//        return this.foodRepo.deleteFood(id);
//    }
//
//    @Override
//    public boolean deleteAllFood(int id) {
//        return this.foodRepo.deleteAllFood(id);
//    }
//
////    @Override
////    public ThucAn addFood(Map<String, String> params, MultipartFile file) {
////        ThucAn f = new ThucAn();
////        f.setName(params.get("name"));
////        f.setSoLuong(Integer.parseInt(params.get("soLuong")));
////        f.setPrice(Long.parseLong(params.get("price")));
////        f.setIdLoai(this.cateService.getCateById(Integer.parseInt(params.get("idLoai"))));
////        f.setIdChiNhanh(this.storeService.getChiNhanhById(Integer.parseInt(params.get("idChiNhanh"))));
////        f.setCreatedDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
////        if (!f.getFile().isEmpty()) {
////            try {
////                Map res = this.cloudinary.uploader().upload(f.getFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
////                f.setImage(res.get("secure_url").toString());
////            } catch (IOException ex) {
////                Logger.getLogger(FoodServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
////            }
////        }
////        return f;
////    }
//    

    @Override
    public void save(ThucAn ta) {
        if (ta.getFile() != null && !ta.getFile().isEmpty()) {
            Map res;
            try {
                res = this.cloudinary.uploader().upload(ta.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                ta.setImage(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(FoodServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.foodRepo.save(ta);
    }

    @Override
    public void delete(Long id) {
        ThucAn ta = this.foodRepo.getReferenceById(id);
        this.foodRepo.delete(ta);
    }

    @Override
    public ThucAn getThucAnById(Long id) {
        return this.foodRepo.getReferenceById(id);
    }

    @Override
    public List<ThucAn> getThucAnByChiNhanh(int cnId) {
        return this.cfoodRepo.getThucAnByChiNhanh(cnId);
    }

    @Override
    public List<ThucAn> getThucAns(Map<String, String> params) {
        return this.cfoodRepo.getThucAns(params);
    }
    
    
    
}
