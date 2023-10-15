/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.repository.impl;

import com.thao.pojo.HoaDon;
import com.thao.pojo.HoaDonChiTiet;
import com.thao.pojo.HoaDonChiTietTaiCho;
import com.thao.pojo.HoaDonTaiCho;
import com.thao.pojo.MonDat;
import com.thao.pojo.MonDatTaiCho;
import com.thao.pojo.NguoiDung;
import com.thao.pojo.ThucAn;
import com.thao.repository.CustomNguoiDungRepository;
import com.thao.repository.FoodRepository;
import com.thao.repository.HoaDonChiTietRepository;
import com.thao.repository.HoaDonChiTietTaiChoRepository;
import com.thao.repository.HoaDonRepository;
import com.thao.repository.HoaDonTaiChoRepository;
import com.thao.repository.NguoiDungRepository;
import com.thao.repository.ReceiptRepository;
import com.thao.service.BanService;
import com.thao.service.EmailService;
import jakarta.persistence.EntityManager;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Chung Vu
 */
@Repository
@EnableAsync
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
    @Autowired
    private HoaDonTaiChoRepository hdtcRepo;
    @Autowired
    private HoaDonChiTietTaiChoRepository hdcttcRepo;
    @Autowired
    private EmailService emailSer;
    @Autowired
    private BanService banSer;


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
                hdct.setGiaVanChuyen(Long.parseLong("10000"));
                hdct.setTongTien(Long.parseLong(Integer.toString(m.getSoLuong()*m.getDonGia()+10000)));
                hdct.setCreatedDate(new Date());
                
                this.hdctRepo.save(hdct);
                ThucAn ta = this.foodRepo.getReferenceById(Long.parseLong(Integer.toString(m.getIdThucAn())));
                ta.setSoLuong(ta.getSoLuong() - m.getSoLuong());
                this.foodRepo.save(ta);
            }
            this.emailSer.sendSimpleMessage(nd.getEmail(), "Cảm ơn bạn đã mua hàng", "Cảm ơn bạn đã mua hàng");
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addReceiptOff(Map<String, MonDatTaiCho> carts) {
        HoaDonTaiCho hd = new HoaDonTaiCho();
        try{
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            NguoiDung nd = this.ndRepo.getNDByUsername(authentication.getName());
            hd.setCreatedDate(new Date());
            hd.setIdBan(this.banSer.getBanById(Long.parseLong(Integer.toString(carts.get(carts.keySet().toArray()[0]).getIdBan()))));
//            hd.setIdNguoiDung(nd);
            this.hdtcRepo.save(hd);
            
            for(MonDatTaiCho m : carts.values()){
                HoaDonChiTietTaiCho hdct = new HoaDonChiTietTaiCho();
                hdct.setIdThucAn(this.foodRepo.getReferenceById(Long.parseLong(Integer.toString(m.getIdThucAn()))));
                hdct.setIdHoaDon(hd);
                hdct.setSoLuongMua(m.getSoLuong());
                hdct.setTongTien(Long.parseLong(Integer.toString(m.getSoLuong()*m.getDonGia())));
                hdct.setCreatedDate(new Date());
                
                this.hdcttcRepo.save(hdct);
                ThucAn ta = this.foodRepo.getReferenceById(Long.parseLong(Integer.toString(m.getIdThucAn())));
                ta.setSoLuong(ta.getSoLuong() - m.getSoLuong());
                this.foodRepo.save(ta);
            }
            
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
}
