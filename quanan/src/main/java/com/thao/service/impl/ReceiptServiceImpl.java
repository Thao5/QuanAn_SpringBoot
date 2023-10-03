/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.service.impl;

import com.thao.pojo.MonDat;
import com.thao.pojo.MonDatTaiCho;
import com.thao.repository.ReceiptRepository;
import com.thao.service.ReceiptService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Chung Vu
 */
@Service
public class ReceiptServiceImpl implements ReceiptService{

    @Autowired
    private ReceiptRepository reRepo;
    
    @Override
    public boolean addReceipt(Map<String, MonDat> carts) {
        return this.reRepo.addReceipt(carts);
    }

    @Override
    public boolean addReceiptOff(Map<String, MonDatTaiCho> carts) {
        return this.reRepo.addReceiptOff(carts);
    }
    
}
