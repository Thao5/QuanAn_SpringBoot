/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.thao.service;

import com.paypal.api.payments.Payment;
import com.thao.configs.JwtSecurityConfig;
import com.thao.pojo.HoaDon;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Chung Vu
 */
public interface HoaDonService {
    List<HoaDon> getHoaDons();
    void save(HoaDon hd);
    void delete(Long id);
    HoaDon getHoaDonById(Long id);
    List<HoaDon> getHDs(Map<String,String> params);
    Payment createPayment(Long total, String currency, JwtSecurityConfig.PaypalPaymentMethod method, JwtSecurityConfig.PaypalPaymentIntent intent, String description, String cancelUrl, String successUrl);
    Payment executePayment(String paymentId, String payerId);
}
