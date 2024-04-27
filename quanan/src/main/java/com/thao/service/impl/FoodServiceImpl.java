/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.thao.pojo.Category;
import com.thao.pojo.ThucAn;
import com.thao.repository.CategoryRepository;
import com.thao.repository.ChiNhanhRepository;
import com.thao.repository.CustomChiNhanhRepository;
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
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepo;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private CustomThucAnRepository cfoodRepo;
    @Autowired
    private ChiNhanhRepository CnRepo;
    @Autowired
    private CategoryRepository cateRepo;

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
    public ThucAn getThucAnById2(Long id) {
        return this.foodRepo.findById(id).get();
    }

    @Override
    public List<ThucAn> getThucAnByChiNhanh(int cnId) {
        return this.cfoodRepo.getThucAnByChiNhanh(cnId);
    }

    @Override
    public List<ThucAn> getThucAns(Map<String, String> params) {
        return this.cfoodRepo.getThucAns(params);
    }
    
    @Override
    public List<ThucAn> getThucAnsAll(Map<String, String> params) {
        return this.cfoodRepo.getThucAnsAll(params);
    }

    @Override
    public ThucAn addFood(Map<String, String> params, MultipartFile avatar) {
        ThucAn food = new ThucAn();
        food.setCreatedDate(new Date());
        food.setActive(Boolean.TRUE);
        food.setIdChiNhanh(this.CnRepo.getReferenceById(Long.parseLong(String.valueOf(4))));
        food.setName(params.get("name"));
        food.setPrice(Long.parseLong(params.get("price")));
        food.setSoLuong(Integer.parseInt(params.get("soLuong")));
        Category c = this.cateRepo.findById(Long.parseLong(params.get("idLoai"))).get();
        food.setIdLoai(c);
        if (!avatar.isEmpty()) {
            Map res;
            try {
                res = this.cloudinary.uploader().upload(avatar.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                food.setImage(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(NguoiDungServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        this.foodRepo.save(food);
        return food;
    }

    @Override
    public ThucAn updateFood(Long id, Map<String, String> params) {
        ThucAn food = this.foodRepo.findById(id).get();
        if (params != null) {
            if (params.get("active") != null) {
                food.setActive(Boolean.parseBoolean(params.get("active")));
            }
            if (params.get("name") != null && !params.get("name").trim().equals("") && !params.get("name").equals(food.getName())) {
                food.setName(params.get("name"));
            }
            if (params.get("price") != null && !params.get("price").trim().equals("")) {
                food.setPrice(Long.parseLong(params.get("price")));
            }
            if (params.get("soLuong") != null && !params.get("soLuong").trim().equals("")) {
                food.setSoLuong(Integer.parseInt(params.get("soLuong")));
            }
            if (params.get("idLoai") != null && !params.get("idLoai").trim().equals("")) {
                Category c = this.cateRepo.findById(Long.parseLong(params.get("idLoai"))).get();
                food.setIdLoai(c);
            }
            this.foodRepo.save(food);
        }
        return food;
    }

    @Override
    public ThucAn updateFoodAvatar(Long id, MultipartFile avatar) {
        ThucAn food = this.foodRepo.findById(id).get();
        if (!avatar.isEmpty()) {
            Map res;
            try {
                res = this.cloudinary.uploader().upload(avatar.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                food.setImage(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(NguoiDungServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        this.foodRepo.save(food);
        return food;
    }

    
}
