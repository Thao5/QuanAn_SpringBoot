/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.repository.impl;

import com.thao.pojo.HoaDon;
import com.thao.pojo.HoaDonChiTiet;
import com.thao.pojo.MonDat;
import com.thao.pojo.NguoiDung;
import com.thao.pojo.ThucAn;
import com.thao.repository.CustomNguoiDungRepository;
import com.thao.repository.FoodRepository;
import com.thao.repository.HoaDonChiTietRepository;
import com.thao.repository.HoaDonRepository;
import com.thao.repository.NguoiDungRepository;
import com.thao.repository.ReceiptRepository;
import jakarta.persistence.EntityManager;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Chung Vu
 */
@Repository
public class ReceiptRepositoryImpl implements ReceiptRepository{
    @Autowired
    private CustomNguoiDungRepository ndRepo;
    @Autowired
    private FoodRepository foodRepo;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private HoaDonRepository hdRepo;
    @Autowired
    private HoaDonChiTietRepository hdctRepo;

    @Override
    public boolean addReceipt(Map<String, MonDat> carts) {
        HoaDon hd = new HoaDon();
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            NguoiDung nd = this.ndRepo.getNDByUsername(authentication.getName());
            hd.setCreatedDate(new Date());
            hd.setIdNguoiDung(nd);
            this.hdRepo.save(hd);
            
            for(MonDat m : carts.values()){
                HoaDonChiTiet hdct = new HoaDonChiTiet();
                hdct.setIdThucAn(this.foodRepo.getReferenceById(Long.parseLong(Integer.toString(m.getIdThucAn()))));
                hdct.setIdHoaDon(hd);
                hdct.setSoLuongMua(m.getSoLuong());
                hdct.setTongTien(Long.parseLong(Integer.toString(m.getSoLuong()*m.getDonGia())));
                hdct.setCreatedDate(new Date());
                
                this.hdctRepo.save(hdct);
            }
            
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
}