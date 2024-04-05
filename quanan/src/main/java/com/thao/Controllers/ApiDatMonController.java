/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thao.Controllers;

import static com.thao.Controllers.ApiThanhToanOnlineController.sessionTmp;
import com.thao.pojo.MonDat;
import com.thao.service.ReceiptService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
public class ApiDatMonController {

    @Autowired
    private ReceiptService receiptService;

    @PostMapping("/pay/")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public void add(@RequestBody Map<String, MonDat> carts) {
        this.receiptService.addReceipt(carts);
    }

    @GetMapping("/pay/pay_online/")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public void addPaypal(HttpServletResponse res) {
        if (sessionTmp != null) {
            this.receiptService.addReceipt((Map<String, MonDat>) sessionTmp.getAttribute("carts"));
        }
        try {
            res.sendRedirect("http://localhost:4200/");
            res.setStatus(302);
        } catch (IOException ex) {
            Logger.getLogger(ApiDatMonController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
