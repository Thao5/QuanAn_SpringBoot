/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import com.thao.pojo.MonDatTaiCho;
import com.thao.service.ReceiptService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Chung Vu
 */
@RestController
@RequestMapping("/api")
public class ApiDatMonTaiChoController {
    @Autowired
    private ReceiptService receiptService;
    
    @PostMapping("/payoffline/")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public void add(@RequestBody Map<String, MonDatTaiCho> carts) {
        this.receiptService.addReceiptOff(carts);
    }
}
