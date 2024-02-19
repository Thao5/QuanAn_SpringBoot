/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.service.impl;

import com.paypal.api.payments.Payment;
import com.thao.configs.JwtSecurityConfig;
import com.thao.pojo.HoaDon;
import com.thao.repository.CustomHoaDonRepository;
import com.thao.repository.HoaDonRepository;
import com.thao.service.HoaDonService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Chung Vu
 */
@Service
public class HoaDonServiceImpl implements HoaDonService{
    @Autowired
    private HoaDonRepository hdRepo;
    @Autowired
    private CustomHoaDonRepository cusHDRepo;

    @Override
    public List<HoaDon> getHoaDons() {
        return this.hdRepo.findAll();
    }

    @Override
    public void save(HoaDon hd) {
        if(hd.getId() == null){
            hd.setCreatedDate(new Date());
        }
        this.hdRepo.save(hd);
    }

    @Override
    public void delete(Long id) {
        HoaDon hd = this.hdRepo.getReferenceById(id);
        this.hdRepo.delete(hd);;
    }

    @Override
    public HoaDon getHoaDonById(Long id) {
        return this.hdRepo.getReferenceById(id);
    }

    @Override
    public List<HoaDon> getHDs(Map<String, String> params) {
        return this.cusHDRepo.getHDs(params);
    }

    @Override
    public Payment createPayment(Long total, String currency, JwtSecurityConfig.PaypalPaymentMethod method, JwtSecurityConfig.PaypalPaymentIntent intent, String description, String cancelUrl, String successUrl) {
        return this.cusHDRepo.createPayment(total, currency, method, intent, description, cancelUrl, successUrl);
    }

    @Override
    public Payment executePayment(String paymentId, String payerId) {
        return this.cusHDRepo.executePayment(paymentId, payerId);
    }
    
    
    
}
